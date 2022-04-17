package com.example.demo.controller;


import com.example.demo.dto.CheckValidityResponse;
import com.example.demo.dto.CsrDTO;
import com.example.demo.dto.InvalidateCertificateRequest;
import com.example.demo.dto.ReadCertificateResponse;
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
import java.util.List;

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
    public List<ReadCertificateResponse> read() {
        var certificates = certificatesService.read();
        return toReadResponse.convert(certificates);
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

    @DeleteMapping(value = "/csr/{id}")
    public void deleteCSR(@PathVariable(name="id") Integer id){
        this.certificatesService.deleteCsr(id);
    }

}
