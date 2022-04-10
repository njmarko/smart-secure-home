package com.example.demo.service;

import com.example.demo.model.CertificateData;

import java.math.BigInteger;

public interface CertificateDataService {
    CertificateData read(BigInteger serialNumber);

    CertificateData readNonCancelled(BigInteger serialNumber);

    void invalidate(BigInteger serialNumber);
}
