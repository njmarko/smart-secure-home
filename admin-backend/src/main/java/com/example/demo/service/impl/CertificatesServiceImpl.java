package com.example.demo.service.impl;

import com.example.demo.dto.CsrSignDataDTO;
import com.example.demo.dto.SignatureAlgEnumDTO;
import com.example.demo.model.CSR;
import com.example.demo.model.CertificateData;
import com.example.demo.model.RevocationReason;
import com.example.demo.repository.CSRRepository;
import com.example.demo.service.CertificateDataService;
import com.example.demo.service.CertificatesService;
import com.example.demo.service.KeystoreService;
import com.example.demo.service.X500DetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CertificatesServiceImpl implements CertificatesService {
    private final KeystoreService keystoreService;
    private final CertificateDataService certificateDataService;
    private final X500DetailsService x500DetailsService;
    private final CSRRepository csrRepository;

    @Value("${keystore.name}")
    private String keystoreFile;

    @Value("${keystore.password}")
    private String keystorePassword;

    @Value("${keystore.superAdmin.alias}")
    private String superAdminAlias;

    @Value("${keystore.superAdmin.password}")
    private String superAdminPassword;

    @Autowired
    public CertificatesServiceImpl(KeystoreService keystoreService, CertificateDataService certificateDataService,
                                   X500DetailsService x500DetailsService, CSRRepository csrRepository) {
        this.keystoreService = keystoreService;
        this.certificateDataService = certificateDataService;
        this.x500DetailsService = x500DetailsService;
        this.csrRepository = csrRepository;
    }

    @PostConstruct
    public void init() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @Override
    public List<X509Certificate> read() {
        return certificateDataService.readNonInvalidated()
                .stream()
                .map(CertificateData::getAlias)
                .map(keystoreService::readOne)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CSR> readCsrData(Pageable pageable) {
        return csrRepository.findAllByIsActiveTrue(pageable);
    }

    @Override
    public X509Certificate read(Integer serialNumber) {
        var data = certificateDataService.readNonInvalidated(serialNumber);
        var alias = data.getAlias();
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
        }
    }

    @Override
    @Transactional
    public CertificateStatus readCertificateStatus(Integer serialNumber) {
        try {
            var certificate = certificateDataService.read(serialNumber);
            var revocation = certificate.getRevocation();
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
        var csr = readForUpdate(id);
        csr.setIsActive(Boolean.FALSE);
    }

    @Override
    @Transactional
    public void create(CsrSignDataDTO request) throws Exception {
        var csr = readForUpdate(request.getCsr().getId());
        var keyPair = generateKeyPair();
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

    private void signCertificate(KeyPair keyPair, PKCS10CertificationRequest request, CsrSignDataDTO dto, SignatureAlgEnumDTO algo) throws Exception {
        var alias = UUID.randomUUID().toString();
        var certificateData = certificateDataService.save(new CertificateData(alias));
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder(signAlgoFromEnum(algo) + "Encryption");
        builder = builder.setProvider("BC");

        var issuerData = keystoreService.readIssuerFromStore("src/main/resources/static/"+keystoreFile, superAdminAlias, keystorePassword.toCharArray(), superAdminPassword.toCharArray());

        ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());
        X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(
                issuerData.getX500name(), BigInteger.valueOf(certificateData.getId()), dto.getValidityStart(),
                dto.getValidityEnd(), request.getSubject(), request.getSubjectPublicKeyInfo());

        // Add extensions
        // TODO: Drugi parametera je isCritial vrednost
        var extensions = dto.getExtensions();
        // Basic constraints
        var basicConstraints = extensions.getBasicConstraints();
        if (basicConstraints.getIsUsed()) {
            certificateBuilder.addExtension(Extension.basicConstraints, false, new BasicConstraints(basicConstraints.getSubjectIsCa()));
        }
        // Key usage
        var keyUsage = extensions.getKeyUsage();
        if (keyUsage.getIsUsed()) {
            certificateBuilder.addExtension(Extension.keyUsage, false, new KeyUsage(keyUsage.toBitMask()));
        }
        // Extended key usage
        var extendedKeyUsage = extensions.getExtendedKeyUsage();
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

        java.security.cert.Certificate[] iusserCertificates = keystoreService.readCertificateChain("src/main/resources/static/"+keystoreFile, keystorePassword, superAdminAlias);
        List<java.security.cert.Certificate> issuerCertChain = Arrays.stream(iusserCertificates).collect(Collectors.toList());
        issuerCertChain.add(0, cert);
        java.security.cert.Certificate[] chain = issuerCertChain.toArray(new java.security.cert.Certificate[0]);

        keystoreService.writeChain(alias, keyPair.getPrivate(), superAdminPassword.toCharArray(), chain);
        keystoreService.saveKeyStore("src/main/resources/static/"+keystoreFile, keystorePassword.toCharArray());
    }

    private PKCS10CertificationRequest fromDto(CSR csr, KeyPair keyPair, SignatureAlgEnumDTO algo) throws Exception {
        var builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, csr.getCommonName());
        builder.addRDN(BCStyle.O, csr.getOrganization());
        builder.addRDN(BCStyle.OU, csr.getOrganizationUnit());
        builder.addRDN(BCStyle.C, csr.getCountry());
        builder.addRDN(BCStyle.EmailAddress, csr.getEmail());
        builder.addRDN(BCStyle.L, csr.getLocale());
        builder.addRDN(BCStyle.ST, csr.getState());
        var name = builder.build();
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
        var keyGen = KeyPairGenerator.getInstance("RSA");
        var random = SecureRandom.getInstance("SHA1PRNG", "SUN");
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
