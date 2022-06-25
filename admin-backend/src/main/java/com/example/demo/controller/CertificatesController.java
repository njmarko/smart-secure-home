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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.cert.X509Certificate;
import java.util.Objects;

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

    @PreAuthorize("hasAuthority('READ_CERTIFICATES')")
    @GetMapping
        public Page<ReadCertificateResponse> read(@PageableDefault Pageable pageable) {
        Page<X509Certificate> certificates = certificatesService.read(pageable);
        return certificates.map(toReadResponse::convert);
    }

    @PreAuthorize("hasAuthority('SIGN_CSR')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCertificate(@RequestBody CsrSignDataDTO csrSignDataDTO) throws Exception {
        certificatesService.create(csrSignDataDTO);
    }

    @PreAuthorize("hasAuthority('READ_CERTIFICATES')")
    @GetMapping(value = "/{serialNumber}")
    public ReadCertificateResponse read(@PathVariable Integer serialNumber) {
    	X509Certificate certificate = certificatesService.read(serialNumber);
        return toReadResponse.convert(certificate);
    }

    @PreAuthorize("hasAuthority('READ_CERTIFICATES')")
    @GetMapping(value = "/{serialNumber}/validity")
    public CheckValidityResponse validity(@PathVariable Integer serialNumber) {
        X509Certificate certificate = certificatesService.read(serialNumber);
        return toCheckValidityResponse.convert(certificate);
    }

    @PreAuthorize("hasAuthority('READ_CERTIFICATES')")
    @GetMapping(value = "/{serialNumber}/status")
    public CertificateStatus getCertificateStatus(@PathVariable Integer serialNumber) {
        return certificatesService.readCertificateStatus(serialNumber);
    }

    @PreAuthorize("hasAuthority('READ_CERTIFICATES')")
    @PostMapping(value = "/{serialNumber}/invalidate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invalidate(@PathVariable Integer serialNumber, @RequestBody InvalidateCertificateRequest request) {
        certificatesService.invalidate(serialNumber, request.getReason());
    }

    @GetMapping(value = "csr-verification")
    public void verifyCSR(@RequestParam("token") String token) {
        if (Objects.isNull(token)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        certificatesService.verifyCSR(token);
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

    @PreAuthorize("hasAuthority('READ_CSRS')")
    @GetMapping(value = "/csr")
    public Page<CsrDTO> getCertificateStatus(@PageableDefault Pageable pageable) {
        Page<CSR> page = certificatesService.readCsrData(pageable);
        return page.map(csrCsrDTOEntityConverter::convert);
    }

    @PreAuthorize("hasAuthority('READ_CSRS')")
    @GetMapping(value = "/csr/{id}")
    public CsrDTO getCsr(@PathVariable(name = "id") Integer id) {
        return csrCsrDTOEntityConverter.convert(this.certificatesService.readCsr(id));
    }

    @PreAuthorize("hasAuthority('READ_CSRS')")
    @DeleteMapping(value = "/csr/{id}")
    public void deleteCSR(@PathVariable(name = "id") Integer id) {
        this.certificatesService.deleteCsr(id);
    }

}
