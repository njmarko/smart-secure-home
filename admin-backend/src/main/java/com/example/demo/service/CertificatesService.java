package com.example.demo.service;

import java.io.InputStream;

public interface CertificatesService {
    void showKeyStoreContent();
    String readCertificateSigningRequest(InputStream stream);

}
