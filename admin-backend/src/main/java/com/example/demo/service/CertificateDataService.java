package com.example.demo.service;

import com.example.demo.model.CertificateData;

import java.math.BigInteger;
import java.util.List;

public interface CertificateDataService {
    CertificateData read(BigInteger serialNumber);

    CertificateData readNonCancelled(BigInteger serialNumber);

    void invalidate(BigInteger serialNumber);

    List<CertificateData> readNonInvalidated();
}
