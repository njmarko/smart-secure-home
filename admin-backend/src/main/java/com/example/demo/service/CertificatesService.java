package com.example.demo.service;

import com.example.demo.dto.CsrSignDataDTO;
import com.example.demo.model.CSR;
import com.example.demo.model.RevocationReason;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.cert.X509Certificate;

public interface CertificatesService {

    Page<X509Certificate> read(Pageable pageable);

    Page<CSR> readCsrData(Pageable pageable);

    X509Certificate read(Integer serialNumber);

    void invalidate(Integer serialNumber, RevocationReason reason);

    void saveCSR(CSR csr);

    void createCSR(String pemCSR);

    CertificateStatus readCertificateStatus(Integer serialNumber);

    CSR readForUpdate(Integer id);

    void deleteCsr(Integer id);

    void create(CsrSignDataDTO request) throws Exception;

    CSR readCsr(Integer id);

    void verifyCSR(String token);
}
