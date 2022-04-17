package com.example.demo.service;

import com.example.demo.dto.CsrSignDataDTO;
import com.example.demo.model.CSR;
import com.example.demo.model.RevocationReason;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.cert.X509Certificate;
import java.util.List;

public interface CertificatesService {

    List<X509Certificate> read();

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
}
