package com.example.demo.service.impl;

import com.example.demo.model.CertificateData;
import com.example.demo.model.Revocation;
import com.example.demo.repository.CertificateDataRepository;
import com.example.demo.repository.RevocationRepository;
import com.example.demo.service.CertificateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionScoped;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CertificateDataServiceImpl implements CertificateDataService {
    private final CertificateDataRepository certificateDataRepository;
    private final RevocationRepository revocationRepository;

    @Autowired
    public CertificateDataServiceImpl(CertificateDataRepository certificateDataRepository, RevocationRepository revocationRepository) {
        this.certificateDataRepository = certificateDataRepository;
        this.revocationRepository = revocationRepository;
    }

    @Override
    public CertificateData read(Integer serialNumber) {
        return certificateDataRepository.readBySerialNumber(serialNumber).orElseThrow(
                () -> new RuntimeException(String.format("Cannot find certificate with serial number: %s", serialNumber))
        );
    }

    @Override
    public CertificateData readNonInvalidated(Integer serialNumber) {
        return certificateDataRepository.readBySerialNumberNonCancelled(serialNumber).orElseThrow(
                () -> new RuntimeException(String.format("Cannot find certificate with serial number: %s", serialNumber))
        );
    }

    @Override
    @Transactional(readOnly = false)
    public CertificateData save(CertificateData certificateData) {
        return certificateDataRepository.save(certificateData);
    }

    @Override
    public List<CertificateData> readNonInvalidated() {
        return certificateDataRepository.readNonInvalidated();
    }

    @Override
    @Transactional(readOnly = false)
    public void invalidate(Integer serialNumber, String reason) {
        var data = read(serialNumber);
        var revocation = new Revocation(data, reason);
        data.setRevocation(revocation);
        revocationRepository.save(revocation);
    }
}
