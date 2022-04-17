package com.example.demo.service;

import com.example.demo.model.CertificateData;
import com.example.demo.model.RevocationReason;

import java.util.List;

public interface CertificateDataService {
    CertificateData save(CertificateData certificateData);

    CertificateData read(Integer serialNumber);

    CertificateData readNonInvalidated(Integer serialNumber);

    void invalidate(Integer serialNumber, RevocationReason reason);

    List<CertificateData> readNonInvalidated();
}
