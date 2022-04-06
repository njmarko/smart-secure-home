package com.example.demo.service;

import com.example.demo.model.IssuerData;

import java.security.PrivateKey;
import java.security.cert.Certificate;

public interface KeystoreService {
    IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass);

    Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias);

    Certificate[] readCertificateChain(String keyStoreFile, String keyStorePass, String alias);

    PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass);

    void loadKeystore(String fileName, char[] password);

    void listContent(String fileName, char[] password);
}
