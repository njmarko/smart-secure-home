package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.model.CSR;
import com.example.demo.service.CertificatesService;
import com.example.demo.support.EntityConverter;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.cert.X509Certificate;

@RestController
@RequestMapping("api/certificates")
public class CertificatesController {
    private final CertificatesService certificatesService;

    private final EntityConverter<X509Certificate, ReadCertificateResponse> toReadResponse;
    private final EntityConverter<X509Certificate, CheckValidityResponse> toCheckValidityResponse;
    private final EntityConverter<CsrDTO, CSR> csrDTOCSREntityConverter;
    private final EntityConverter<CSR, CsrDTO> csrCsrDTOEntityConverter;


    @Autowired
    public CertificatesController(CertificatesService certificatesService,
                                  EntityConverter<X509Certificate, ReadCertificateResponse> toReadResponse,
                                  EntityConverter<X509Certificate, CheckValidityResponse> toCheckValidityResponse,
                                  EntityConverter<CsrDTO, CSR> csrDTOCSREntityConverter,
                                  EntityConverter<CSR, CsrDTO> csrCsrDTOEntityConverter) {
        this.certificatesService = certificatesService;
        this.toReadResponse = toReadResponse;
        this.toCheckValidityResponse = toCheckValidityResponse;
        this.csrDTOCSREntityConverter = csrDTOCSREntityConverter;
        this.csrCsrDTOEntityConverter = csrCsrDTOEntityConverter;
    }

    @GetMapping
    public Page<ReadCertificateResponse> read(@PageableDefault Pageable pageable) {
        var certificates = certificatesService.read(pageable);
        return certificates.map(toReadResponse::convert);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCertificate(@RequestBody CsrSignDataDTO csrSignDataDTO) throws Exception {
        //TODO pravljenje sertifikata
        certificatesService.create(csrSignDataDTO);
    }

    @GetMapping(value = "/{serialNumber}")
    public ReadCertificateResponse read(@PathVariable Integer serialNumber) {
        var certificate = certificatesService.read(serialNumber);
        return toReadResponse.convert(certificate);
    }

    @GetMapping(value = "/{serialNumber}/validity")
    public CheckValidityResponse validity(@PathVariable Integer serialNumber) {
        // TODO: Maybe here we want to keep some mapping between alias and certificate's serial number
        var certificate = certificatesService.read(serialNumber);
        return toCheckValidityResponse.convert(certificate);
    }

    @GetMapping(value = "/{serialNumber}/status")
    public CertificateStatus getCertificateStatus(@PathVariable Integer serialNumber) {
        return certificatesService.readCertificateStatus(serialNumber);
    }

    @PostMapping(value = "/{serialNumber}/invalidate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invalidate(@PathVariable Integer serialNumber, @RequestBody InvalidateCertificateRequest request) {
        certificatesService.invalidate(serialNumber, request.getReason());
    }

    @PostMapping(value = "/generateCSR")
    @ResponseStatus(HttpStatus.CREATED)
    public void generateCSR(@RequestBody CsrDTO csrDTO) {
        this.certificatesService.saveCSR(csrDTOCSREntityConverter.convert(csrDTO));
    }

    @PostMapping(value = "/addCSR")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCSR(@RequestBody String pemCSR) {
        this.certificatesService.createCSR(pemCSR);
    }

    @GetMapping(value = "/csr")
    public Page<CsrDTO> getCertificateStatus(@PageableDefault Pageable pageable) {
        var page = certificatesService.readCsrData(pageable);
        return page.map(csrCsrDTOEntityConverter::convert);
    }

    @GetMapping(value = "/csr/{id}")
    public CsrDTO getCsr(@PathVariable(name = "id") Integer id) {
        return csrCsrDTOEntityConverter.convert(this.certificatesService.readCsr(id));
    }

    @DeleteMapping(value = "/csr/{id}")
    public void deleteCSR(@PathVariable(name = "id") Integer id) {
        this.certificatesService.deleteCsr(id);
    }

}
