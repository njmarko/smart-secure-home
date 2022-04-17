package com.example.demo.service;

import com.example.demo.model.CSR;
import com.example.demo.model.RevocationReason;
import org.bouncycastle.cert.ocsp.CertificateStatus;

import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.List;

public interface CertificatesService {

    List<X509Certificate> read();

    X509Certificate read(Integer serialNumber);

    void invalidate(Integer serialNumber, RevocationReason reason);

    // TODO: Obrisati ovo kad se sredi kod
    void showKeyStoreContent();

    String readCertificateSigningRequest(InputStream stream);

    void saveCSR(CSR csr);

    void createCSR(String pemCSR);

    CertificateStatus readCertificateStatus(Integer serialNumber);
}
