package com.example.demo.service.impl;

import com.example.demo.model.IssuerData;
import com.example.demo.service.CertificatesService;
import com.example.demo.service.KeystoreService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;

@Service
public class CertificatesServiceImpl implements CertificatesService {


	private final KeystoreService reader;

	@Autowired
	public CertificatesServiceImpl(KeystoreService reader) {
		this.reader = reader;
	}

	public void showKeyStoreContent() {
		Scanner keyboard = new Scanner(System.in);
		String keystoreName = "";
		String keystorePassword = "";
		System.out.println("===== Unesite ime za keystore =====");
		keystoreName = keyboard.nextLine();
		System.out.println("===== Unesite password za keystore =====");
		keystorePassword = keyboard.nextLine();

		reader.listContent("src/main/resources/static/" + keystoreName, keystorePassword.toCharArray());
		System.out.println("Kraj");
	}

	public String readCertificateSigningRequest(InputStream csrStream) {
		Logger LOG = Logger.getLogger(CertificatesServiceImpl.class.getName());

		final String COUNTRY = "2.5.4.6";
		final String STATE = "2.5.4.8";
		final String LOCALE = "2.5.4.7";
		final String ORGANIZATION = "2.5.4.10";
		final String ORGANIZATION_UNIT = "2.5.4.11";
		final String COMMON_NAME = "2.5.4.3";
		final String EMAIL = "2.5.4.9";

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
			System.out.println("COUNTRY: " + getX500Field(COUNTRY, x500Name));
			System.out.println("STATE: " + getX500Field(STATE, x500Name));
			System.out.println("LOCALE: " + getX500Field(LOCALE, x500Name));
			System.out.println("ORGANIZATION: " + getX500Field(ORGANIZATION, x500Name));
			System.out.println("ORGANIZATION_UNIT: " + getX500Field(ORGANIZATION_UNIT, x500Name));
			System.out.println("COMMON_NAME: " + getX500Field(COMMON_NAME, x500Name));
			System.out.println("EMAIL: " + getX500Field(EMAIL, x500Name));

			String keystoreName = "proba";
			String keystorePassword = "proba";
			String alias = "self";
			String privateKeyPass = "self";

			IssuerData issuerData = reader.readIssuerFromStore("src/main/resources/static/" + keystoreName, alias, keystorePassword.toCharArray(), privateKeyPass.toCharArray());
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

			reader.loadKeystore("src/main/resources/static/" + keystoreName, "proba".toCharArray());
			reader.writeCertificate("codal", newCert);
			reader.saveKeyStore("src/main/resources/static/" + keystoreName, "proba".toCharArray());

		}
		return compname;
	}


	private String getX500Field(String asn1ObjectIdentifier, X500Name x500Name) {
		RDN[] rdnArray = x500Name.getRDNs(new ASN1ObjectIdentifier(asn1ObjectIdentifier));

		String retVal = null;
		for (RDN item : rdnArray) {
			retVal = item.getFirst().getValue().toString();
		}
		return retVal;
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
				System.currentTimeMillis() + 30 * 365 * 24 * 60 * 60
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
	}

}
