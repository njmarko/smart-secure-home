package com.example.demo.service;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.List;

public interface CertificatesService {

    List<X509Certificate> read();

    X509Certificate read(BigInteger serialNumber);

    void invalidate(BigInteger serialNumber);

    // TODO: Obrisati ovo kad se sredi kod
    void showKeyStoreContent();

    String readCertificateSigningRequest(InputStream stream);
}
