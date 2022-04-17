package com.example.demo.service;

import com.example.demo.model.CSR;

import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.List;

public interface CertificatesService {

    List<X509Certificate> read();

    X509Certificate read(Integer serialNumber);

    void invalidate(Integer serialNumber, String reason);

    // TODO: Obrisati ovo kad se sredi kod
    void showKeyStoreContent();

    String readCertificateSigningRequest(InputStream stream);

    void saveCSR(CSR csr);
}
