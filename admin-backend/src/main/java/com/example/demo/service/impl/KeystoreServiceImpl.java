package com.example.demo.service.impl;

import com.example.demo.model.IssuerData;
import com.example.demo.service.KeystoreService;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class KeystoreServiceImpl implements KeystoreService {
    private KeyStore keyStore;

    @Value("${keystore.name}")
    private String keystoreName;

    @Value("${keystore.password}")
    private String keystorePassword;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
            loadKeystore("src/main/resources/static/" + keystoreName, keystorePassword.toCharArray());
        } catch (KeyStoreException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public List<X509Certificate> readCertificates() {
        ArrayList<X509Certificate> certificates = new ArrayList<X509Certificate>();
        try {
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                Certificate certificate = keyStore.getCertificate(aliases.nextElement());
                log.info(certificate.toString());
                certificates.add((X509Certificate) certificate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return certificates;
    }

    @Override
    public Optional<X509Certificate> readOne(String alias) {
        try {
            Certificate certificate = keyStore.getCertificate(alias);
            if (certificate == null) {
                return Optional.empty();
            }
            return Optional.of((X509Certificate) certificate);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, password);

            Certificate cert = keyStore.getCertificate(alias);

            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, keyPass);

            X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
            return new IssuerData(issuerName, privateKey);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            if (ks.isKeyEntry(alias)) {
                return ks.getCertificate(alias);
            }
        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Certificate[] readCertificateChain(String keyStoreFile, String keyStorePass, String alias) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile))) {
                ks.load(in, keyStorePass.toCharArray());

                if (ks.isKeyEntry(alias)) {
                    return keyStore.getCertificateChain(alias);
                }
            }

        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
        return new Certificate[0];
    }


    @Override
    public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
        try {

            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile))) {
                ks.load(in, keyStorePass.toCharArray());

                if (ks.isKeyEntry(alias)) {
                    return (PrivateKey) ks.getKey(alias, pass.toCharArray());
                }
            }

        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException | IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void loadKeystore(String fileName, char[] password) {
        try {
            if (fileName != null) {
                keyStore.load(new FileInputStream(fileName), password);
            } else {
                keyStore.load(null, password);
            }
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listContent(String fileName, char[] password) {
        loadKeystore(fileName, password);
        try {
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                System.out.println(keyStore.getCertificate(alias));
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeKeyEntry(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
        try {
            keyStore.setKeyEntry(alias, privateKey, password, new Certificate[]{certificate});
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeCertificate(String alias, Certificate certificate) {
        try {
            keyStore.setCertificateEntry(alias, certificate);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeChain(String alias, PrivateKey privateKey, char[] password, Certificate[] certificate) {
        try {
            keyStore.setKeyEntry(alias, privateKey, password, certificate);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveKeyStore(String fileName, char[] password) {
        try {
            keyStore.store(new FileOutputStream(fileName), password);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }
}
