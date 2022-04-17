package com.example.demo.service.impl;

import com.example.demo.model.CSR;
import com.example.demo.model.CertificateData;
import com.example.demo.model.IssuerData;
import com.example.demo.model.RevocationReason;
import com.example.demo.repository.CSRRepository;
import com.example.demo.service.CertificateDataService;
import com.example.demo.service.CertificatesService;
import com.example.demo.service.KeystoreService;
import com.example.demo.service.X500DetailsService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.RevokedStatus;
import org.bouncycastle.cert.ocsp.UnknownStatus;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CertificatesServiceImpl implements CertificatesService {
    private final KeystoreService keystoreService;
    private final CertificateDataService certificateDataService;
    private final X500DetailsService x500DetailsService;
    private final CSRRepository csrRepository;

    @Autowired
    public CertificatesServiceImpl(KeystoreService keystoreService, CertificateDataService certificateDataService,
                                   X500DetailsService x500DetailsService, CSRRepository csrRepository) {
        this.keystoreService = keystoreService;
        this.certificateDataService = certificateDataService;
        this.x500DetailsService = x500DetailsService;
        this.csrRepository = csrRepository;
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

    public void showKeyStoreContent() {
        Scanner keyboard = new Scanner(System.in);
        String keystoreName = "";
        String keystorePassword = "";
        System.out.println("===== Unesite ime za keystore =====");
        keystoreName = keyboard.nextLine();
        System.out.println("===== Unesite password za keystore =====");
        keystorePassword = keyboard.nextLine();

        keystoreService.listContent("src/main/resources/static/" + keystoreName, keystorePassword.toCharArray());
        System.out.println("Kraj");
    }

    public String readCertificateSigningRequest(InputStream csrStream) {
        Logger LOG = Logger.getLogger(CertificatesServiceImpl.class.getName());

        PKCS10CertificationRequest csr = convertPemToPKCS10CertificationRequest(csrStream);
        String compname = null;

        if (csr == null) {
            LOG.warning("FAIL! conversion of Pem To PKCS10 Certification Request");
        } else {
            X500Name x500Name = csr.getSubject();

            System.out.println("x500Name is: " + x500Name + "\n");

            RDN cn = x500Name.getRDNs(BCStyle.EmailAddress)[0];
            System.out.println(cn.getFirst().getValue().toString());
            System.out.println(x500Name.getRDNs(BCStyle.EmailAddress)[0]);
            System.out.println("COUNTRY: " + x500DetailsService.getCountry(x500Name));
            System.out.println("STATE: " + x500DetailsService.getState(x500Name));
            System.out.println("LOCALE: " + x500DetailsService.getLocale(x500Name));
            System.out.println("ORGANIZATION: " + x500DetailsService.getOrganization(x500Name));
            System.out.println("ORGANIZATION_UNIT: " + x500DetailsService.getOrganizationUnit(x500Name));
            System.out.println("COMMON_NAME: " + x500DetailsService.getCommonName(x500Name));
            System.out.println("EMAIL: " + x500DetailsService.getEmail(x500Name));

            String keystoreName = "proba";
            String keystorePassword = "proba";
            String alias = "self";
            String privateKeyPass = "self";

            IssuerData issuerData = keystoreService.readIssuerFromStore("src/main/resources/static/" + keystoreName, alias, keystorePassword.toCharArray(), privateKeyPass.toCharArray());
            PrivateKey privateKey = issuerData.getPrivateKey();

            X509Certificate newCert = null;

            try {
                newCert = sign(csr, issuerData);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (SignatureException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OperatorCreationException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            }

            keystoreService.loadKeystore("src/main/resources/static/" + keystoreName, "proba".toCharArray());
            keystoreService.writeCertificate("codal", newCert);
            keystoreService.saveKeyStore("src/main/resources/static/" + keystoreName, "proba".toCharArray());

        }
        return compname;
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

    private X509Certificate sign(PKCS10CertificationRequest inputCSR, IssuerData issuerData)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchProviderException, SignatureException, IOException,
            OperatorCreationException, CertificateException {

        // TODO: nakon ovoga certificateData ima polje id koje treba koristiti kao serialNumber
        // TODO: u sustini za alias moze bilo sta da se korsiti bilo sta, ovo je verovatno najlaksa opcija
        var alias = UUID.randomUUID().toString();
        var certificateData = certificateDataService.save(new CertificateData(alias));

        // Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
        // Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
        // Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");

        // Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
        builder = builder.setProvider("BC");

        // Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
        ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

        X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(
                issuerData.getX500name(), new BigInteger("1"), new Date(
                System.currentTimeMillis()), new Date(
                System.currentTimeMillis() + 30L * 365 * 24 * 60 * 60
                        * 1000), inputCSR.getSubject(), inputCSR.getSubjectPublicKeyInfo());
        X509CertificateHolder holder = certificateBuilder.build(contentSigner);

        Certificate eeX509CertificateStructure = holder.toASN1Structure();

        CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");

        // Read Certificate
        InputStream is1 = new ByteArrayInputStream(eeX509CertificateStructure.getEncoded());
        X509Certificate theCert = (X509Certificate) cf.generateCertificate(is1);
        is1.close();
        return theCert;
        //return null;

        // TODO: Ovde treba poslati privatni kljuc?
    }


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

}
