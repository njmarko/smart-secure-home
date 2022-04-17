package com.example.demo.service;

import com.example.demo.model.CertificateData;

import java.util.List;

public interface CertificateDataService {
    CertificateData save(CertificateData certificateData);

    CertificateData read(Integer serialNumber);

    CertificateData readNonInvalidated(Integer serialNumber);

    void invalidate(Integer serialNumber, String reason);

    List<CertificateData> readNonInvalidated();
}
