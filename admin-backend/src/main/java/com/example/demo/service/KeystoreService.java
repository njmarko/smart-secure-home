package com.example.demo.service;

import com.example.demo.model.IssuerData;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Optional;

public interface KeystoreService {
    List<X509Certificate> readCertificates();

    Optional<X509Certificate> readOne(String alias);

    IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass);

    Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias);

    Certificate[] readCertificateChain(String keyStoreFile, String keyStorePass, String alias);

    PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass);

    void loadKeystore(String fileName, char[] password);

    void listContent(String fileName, char[] password);

    void writeKeyEntry(String alias, PrivateKey privateKey, char[] password, Certificate certificate);

    void writeCertificate(String alias, Certificate certificate);

    void saveKeyStore(String fileName, char[] password);

    void writeChain(String alias, PrivateKey privateKey, char[] password, Certificate[] certificate);
}
