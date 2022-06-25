package com.example.demo.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.example.demo.dto.CsrSignDataDTO;
import com.example.demo.dto.SignatureAlgEnumDTO;
import com.example.demo.dto.extensions.BasicConstraintsDTO;
import com.example.demo.dto.extensions.ExtendedKeyUsageDTO;
import com.example.demo.dto.extensions.ExtensionsDTO;
import com.example.demo.dto.extensions.KeyUsageDTO;
import com.example.demo.model.CSR;
import com.example.demo.model.CSRVerificationToken;
import com.example.demo.model.CertificateData;
import com.example.demo.model.IssuerData;
import com.example.demo.model.Revocation;
import com.example.demo.model.RevocationReason;
import com.example.demo.repository.CSRRepository;
import com.example.demo.repository.CSRVerificationTokenRepository;
import com.example.demo.service.CertificateDataService;
import com.example.demo.service.CertificatesService;
import com.example.demo.service.EmailService;
import com.example.demo.service.KeystoreService;
import com.example.demo.service.X500DetailsService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.RevokedStatus;
import org.bouncycastle.cert.ocsp.UnknownStatus;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificatesServiceImpl implements CertificatesService {
    private final KeystoreService keystoreService;
    private final CSRVerificationTokenRepository verificationTokenRepository;
    private final CertificateDataService certificateDataService;
    private final X500DetailsService x500DetailsService;
    private final EmailService emailService;
    private final CSRRepository csrRepository;

    @Value("${keystore.name}")
    private String keystoreFile;

    @Value("${keystore.password}")
    private String keystorePassword;

    @Value("${keystore.superAdmin.alias}")
    private String superAdminAlias;

    @Value("${keystore.superAdmin.password}")
    private String superAdminPassword;

    @PostConstruct
    public void init() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @Override
    public Page<X509Certificate> read(Pageable pageable) {
        Page<CertificateData> results = certificateDataService.readNonInvalidated(pageable);
        return new PageImpl<>(results.getContent()
                .stream()
                .map(CertificateData::getAlias)
                .map(keystoreService::readOne)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList()),
                results.getPageable(), results.getTotalElements());
    }

    @Override
    public Page<CSR> readCsrData(Pageable pageable) {
        return csrRepository.findByIsActiveTrueAndVerifiedTrue(pageable);
    }

    @Override
    public X509Certificate read(Integer serialNumber) {
        CertificateData data = certificateDataService.readNonInvalidated(serialNumber);
        String alias = data.getAlias();
        return keystoreService.readOne(alias).orElseThrow(
                () -> new RuntimeException(String.format("Could not find certificate with alias: %s.", alias))
        );
    }

    @Override
    public void invalidate(Integer serialNumber, RevocationReason reason) {
        certificateDataService.invalidate(serialNumber, reason);
    }

    @Override
    public void saveCSR(CSR csr) {
        csrRepository.save(csr);
        sendVerificationEmail(csr);
    }

    @Override
    public void createCSR(String pemCSR) {
        Logger LOG = Logger.getLogger(CertificatesServiceImpl.class.getName());

        PKCS10CertificationRequest csr = convertPemToPKCS10CertificationRequest(new ByteArrayInputStream(pemCSR.getBytes()));

        if (csr == null) {
            LOG.warning("FAIL! conversion of Pem To PKCS10 Certification Request");
        } else {
            CSR createdCSR = new CSR();
            X500Name x500Name = csr.getSubject();

            createdCSR.setPemCSR(pemCSR);
            createdCSR.setCountry(x500DetailsService.getCountry(x500Name));
            createdCSR.setState(x500DetailsService.getState(x500Name));
            createdCSR.setLocale(x500DetailsService.getLocale(x500Name));
            createdCSR.setOrganization(x500DetailsService.getOrganization(x500Name));
            createdCSR.setOrganizationUnit(x500DetailsService.getOrganizationUnit(x500Name));
            createdCSR.setCommonName(x500DetailsService.getCommonName(x500Name));
            createdCSR.setEmail(x500DetailsService.getEmail(x500Name));

            this.saveCSR(createdCSR);
            this.sendVerificationEmail(createdCSR);
        }
    }

    private void sendVerificationEmail(CSR csr) {
    	CSRVerificationToken token = new CSRVerificationToken(csr);
        verificationTokenRepository.save(token);
        // TODO: Update this :)
        String tokenUrl = "http://localhost:8082/api/certificates/csr-verification?token=" + token;
        String message = String.format("Click on this totally not suspicious link '%s' to verify CSR.", tokenUrl);
        emailService.sendMessage(csr.getEmail(), "CSR verification token", message);
    }

    @Override
    @Transactional
    public CertificateStatus readCertificateStatus(Integer serialNumber) {
        try {
            CertificateData certificate = certificateDataService.read(serialNumber);
            Revocation revocation = certificate.getRevocation();
            if (Objects.isNull(revocation)) {
                return CertificateStatus.GOOD;
            }
            return new RevokedStatus(revocation.getRevocationTime(), revocation.getReason().getValue());
        } catch (Exception ex) {
            return new UnknownStatus();
        }
    }

    @Transactional
    public CSR readForUpdate(Integer id) {
        return this.csrRepository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new RuntimeException("CSR with id: " + id + " not found"));
    }

    @Override
    @Transactional
    public void deleteCsr(Integer id) {
        CSR csr = readForUpdate(id);
        csr.setIsActive(Boolean.FALSE);
    }

    @Override
    @Transactional
    public void create(CsrSignDataDTO request) throws Exception {
        CSR csr = readForUpdate(request.getCsr().getId());
        KeyPair keyPair = generateKeyPair();
        PKCS10CertificationRequest pkcs;
        if (Objects.isNull(csr.getPemCSR())) {
            pkcs = fromDto(csr, keyPair, request.getSignatureAlg());
        } else {
            pkcs = convertPemToPKCS10CertificationRequest(new ByteArrayInputStream(csr.getPemCSR().getBytes()));
        }
        signCertificate(keyPair, pkcs, request, request.getSignatureAlg());
        csr.setIsActive(Boolean.FALSE);
    }

    @Override
    @Transactional
    public CSR readCsr(Integer id) {
        return readForUpdate(id);
    }

    @Override
    @Transactional
    public void verifyCSR(String token) {
        CSRVerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(IllegalArgumentException::new);
        CSR csr = verificationToken.getCsr();
        if (verificationToken.isExpired()) {
            throw new RuntimeException("Token expired.");
        }
        csr.setVerified(true);
    }

    private void signCertificate(KeyPair keyPair, PKCS10CertificationRequest request, CsrSignDataDTO dto, SignatureAlgEnumDTO algo) throws Exception {
        String alias = UUID.randomUUID().toString();
        CertificateData certificateData = certificateDataService.save(new CertificateData(alias));
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder(signAlgoFromEnum(algo) + "Encryption");
        builder = builder.setProvider("BC");

        IssuerData issuerData = keystoreService.readIssuerFromStore("src/main/resources/static/" + keystoreFile, superAdminAlias, keystorePassword.toCharArray(), superAdminPassword.toCharArray());

        ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());
        X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(
                issuerData.getX500name(), BigInteger.valueOf(certificateData.getId()), dto.getValidityStart(),
                dto.getValidityEnd(), request.getSubject(), request.getSubjectPublicKeyInfo());

        // Add extensions
        ExtensionsDTO extensions = dto.getExtensions();
        // Basic constraints
        BasicConstraintsDTO basicConstraints = extensions.getBasicConstraints();
        if (basicConstraints.getIsUsed()) {
            certificateBuilder.addExtension(Extension.basicConstraints, false, new BasicConstraints(basicConstraints.getSubjectIsCa()));
        }
        // Key usage
        KeyUsageDTO keyUsage = extensions.getKeyUsage();
        if (keyUsage.getIsUsed()) {
            certificateBuilder.addExtension(Extension.keyUsage, false, new KeyUsage(keyUsage.toBitMask()));
        }
        // Extended key usage
        ExtendedKeyUsageDTO extendedKeyUsage = extensions.getExtendedKeyUsage();
        if (extendedKeyUsage.getIsUsed()) {
            certificateBuilder.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(extendedKeyUsage.toKeyPurposeIds()));
        }

        X509CertificateHolder holder = certificateBuilder.build(contentSigner);
        Certificate eeX509CertificateStructure = holder.toASN1Structure();
        CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
        // Read Certificate
        InputStream is1 = new ByteArrayInputStream(eeX509CertificateStructure.getEncoded());
        X509Certificate cert = (X509Certificate) cf.generateCertificate(is1);
        is1.close();

        java.security.cert.Certificate[] iusserCertificates = keystoreService.readCertificateChain("src/main/resources/static/" + keystoreFile, keystorePassword, superAdminAlias);
        List<java.security.cert.Certificate> issuerCertChain = Arrays.stream(iusserCertificates).collect(Collectors.toList());
        issuerCertChain.add(0, cert);
        java.security.cert.Certificate[] chain = issuerCertChain.toArray(new java.security.cert.Certificate[0]);

        keystoreService.writeChain(alias, keyPair.getPrivate(), superAdminPassword.toCharArray(), chain);
        keystoreService.saveKeyStore("src/main/resources/static/" + keystoreFile, keystorePassword.toCharArray());
    }

    private PKCS10CertificationRequest fromDto(CSR csr, KeyPair keyPair, SignatureAlgEnumDTO algo) throws Exception {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, csr.getCommonName());
        builder.addRDN(BCStyle.O, csr.getOrganization());
        builder.addRDN(BCStyle.OU, csr.getOrganizationUnit());
        builder.addRDN(BCStyle.C, csr.getCountry());
        builder.addRDN(BCStyle.EmailAddress, csr.getEmail());
        builder.addRDN(BCStyle.L, csr.getLocale());
        builder.addRDN(BCStyle.ST, csr.getState());
        X500Name name = builder.build();
        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(name, keyPair.getPublic());
        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder(signAlgoFromEnum(algo));
        ContentSigner signer = csBuilder.build(keyPair.getPrivate());
        return p10Builder.build(signer);
    }

    private String signAlgoFromEnum(SignatureAlgEnumDTO algo) {
        switch (algo) {
            case SHA_1_WITH_RSA:
                return "SHA1withRSA";
            case SHA_256_WITH_RSA:
                return "SHA256withRSA";
            default:
                return null;
        }
    }

    private KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(2048, random);
        return keyGen.generateKeyPair();
    }

    private PKCS10CertificationRequest convertPemToPKCS10CertificationRequest(InputStream pem) {
        Logger LOG = Logger.getLogger(CertificatesServiceImpl.class.getName());

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        PKCS10CertificationRequest csr = null;
        ByteArrayInputStream pemStream = null;

        pemStream = (ByteArrayInputStream) pem;

        Reader pemReader = new BufferedReader(new InputStreamReader(pemStream));
        PEMParser pemParser = null;
        try {
            pemParser = new PEMParser(pemReader);
            Object parsedObj = pemParser.readObject();
            System.out.println("PemParser returned: " + parsedObj);
            if (parsedObj instanceof PKCS10CertificationRequest) {
                csr = (PKCS10CertificationRequest) parsedObj;
            }
        } catch (IOException ex) {
            LOG.severe("IOException, convertPemToPublicKey");
        } finally {
            if (pemParser != null) {
                IOUtils.closeQuietly(pemParser);
            }
        }
        return csr;
    }

}
