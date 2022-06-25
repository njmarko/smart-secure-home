package com.example.demo.service.impl;

import com.example.demo.model.CertificateData;
import com.example.demo.model.Revocation;
import com.example.demo.model.RevocationReason;
import com.example.demo.repository.CertificateDataRepository;
import com.example.demo.repository.RevocationRepository;
import com.example.demo.service.CertificateDataService;
import com.example.demo.service.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CertificateDataServiceImpl implements CertificateDataService {
    private final CertificateDataRepository certificateDataRepository;
    private final RevocationRepository revocationRepository;
    private final DateTimeService dateTimeService;

    @Autowired
    public CertificateDataServiceImpl(CertificateDataRepository certificateDataRepository,
                                      RevocationRepository revocationRepository,
                                      DateTimeService dateTimeService) {
        this.certificateDataRepository = certificateDataRepository;
        this.revocationRepository = revocationRepository;
        this.dateTimeService = dateTimeService;
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
    public Page<CertificateData> readNonInvalidated(Pageable pageable) {
        return certificateDataRepository.readNonInvalidated(pageable);
    }

    @Override
    @Transactional(readOnly = false)
    public void invalidate(Integer serialNumber, RevocationReason reason) {
        CertificateData data = read(serialNumber);
        Revocation revocation = new Revocation(data, dateTimeService.now(), reason);
        data.setRevocation(revocation);
        revocationRepository.save(revocation);
    }
}
