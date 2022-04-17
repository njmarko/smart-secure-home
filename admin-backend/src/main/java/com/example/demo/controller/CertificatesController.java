package com.example.demo.controller;


import com.example.demo.dto.CheckValidityResponse;
import com.example.demo.dto.CsrDTO;
import com.example.demo.dto.ReadCertificateResponse;
import com.example.demo.model.CSR;
import com.example.demo.service.CertificatesService;
import com.example.demo.support.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.List;

@RestController
@RequestMapping("api/certificates")
public class CertificatesController {
    private final CertificatesService certificatesService;

    private final EntityConverter<X509Certificate, ReadCertificateResponse> toReadResponse;
    private final EntityConverter<X509Certificate, CheckValidityResponse> toCheckValidityResponse;
    private final EntityConverter<CsrDTO, CSR> csrDTOCSREntityConverter;

    @Autowired
    public CertificatesController(CertificatesService certificatesService,
                                  EntityConverter<X509Certificate, ReadCertificateResponse> toReadResponse,
                                  EntityConverter<X509Certificate, CheckValidityResponse> toCheckValidityResponse,
                                  EntityConverter<CsrDTO, CSR> csrDTOCSREntityConverter) {
        this.certificatesService = certificatesService;
        this.toReadResponse = toReadResponse;
        this.toCheckValidityResponse = toCheckValidityResponse;
        this.csrDTOCSREntityConverter = csrDTOCSREntityConverter;
    }

    @GetMapping
    public List<ReadCertificateResponse> read() {
        var certificates = certificatesService.read();
        return toReadResponse.convert(certificates);
    }

    @GetMapping(value = "/{serialNumber}/validity")
    public CheckValidityResponse validity(@PathVariable BigInteger serialNumber) {
        // TODO: Maybe here we want to keep some mapping between alias and certificate's serial number
        var certificate = certificatesService.read(serialNumber);
        return toCheckValidityResponse.convert(certificate);
    }

    @PostMapping(value = "/{serialNumber}/invalidate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invalidate(@PathVariable BigInteger serialNumber) {
        certificatesService.invalidate(serialNumber);
    }

    @PostMapping(value = "/generateCSR")
    @ResponseStatus(HttpStatus.CREATED)
    public void generateCSR(@RequestBody CsrDTO csrDTO) {
        this.certificatesService.saveCSR(csrDTOCSREntityConverter.convert(csrDTO));
    }

}
