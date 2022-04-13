package com.example.demo.service.impl;

import com.example.demo.model.CertificateData;
import com.example.demo.repository.CertificateDataRepository;
import com.example.demo.service.CertificateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CertificateDataServiceImpl implements CertificateDataService {
    private final CertificateDataRepository certificateDataRepository;

    @Autowired
    public CertificateDataServiceImpl(CertificateDataRepository certificateDataRepository) {
        this.certificateDataRepository = certificateDataRepository;
    }

    @Override
    public CertificateData read(BigInteger serialNumber) {
        return certificateDataRepository.readBySerialNumber(serialNumber).orElseThrow(
                () -> new RuntimeException(String.format("Cannot find certificate with serial number: %s", serialNumber))
        );
    }

    @Override
    public CertificateData readNonCancelled(BigInteger serialNumber) {
        return certificateDataRepository.readBySerialNumberNonCancelled(serialNumber).orElseThrow(
                () -> new RuntimeException(String.format("Cannot find certificate with serial number: %s", serialNumber))
        );
    }

    @Override
    public List<CertificateData> readNonInvalidated() {
        return certificateDataRepository.readNonInvalidated();
    }

    @Override
    @Transactional(readOnly = false)
    public void invalidate(BigInteger serialNumber) {
        var data = read(serialNumber);
        data.setIsCancelled(true);
    }
}
