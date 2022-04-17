package com.example.demo.service;

import com.example.demo.model.CertificateData;

import java.math.BigInteger;
import java.util.List;

public interface CertificateDataService {
    CertificateData read(BigInteger serialNumber);

    CertificateData readNonInvalidated(BigInteger serialNumber);

    void invalidate(BigInteger serialNumber, String reason);

    List<CertificateData> readNonInvalidated();
}
