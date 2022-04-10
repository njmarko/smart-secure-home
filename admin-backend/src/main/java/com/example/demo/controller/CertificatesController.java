package com.example.demo.controller;


import com.example.demo.dto.CheckValidityResponse;
import com.example.demo.dto.ReadCertificateResponse;
import com.example.demo.service.CertificatesService;
import com.example.demo.support.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.cert.X509Certificate;
import java.util.List;

@RestController
@RequestMapping("api/certificates")
public class CertificatesController {
    private final CertificatesService certificatesService;

    private final EntityConverter<X509Certificate, ReadCertificateResponse> toReadResponse;
    private final EntityConverter<X509Certificate, CheckValidityResponse> toCheckValidityResponse;

    @Autowired
    public CertificatesController(CertificatesService certificatesService,
                                  EntityConverter<X509Certificate, ReadCertificateResponse> toReadResponse,
                                  EntityConverter<X509Certificate, CheckValidityResponse> toCheckValidityResponse) {
        this.certificatesService = certificatesService;
        this.toReadResponse = toReadResponse;
        this.toCheckValidityResponse = toCheckValidityResponse;
    }

    @GetMapping
    public List<ReadCertificateResponse> read() {
        var certificates = certificatesService.read();
        return toReadResponse.convert(certificates);
    }

    @GetMapping(value = "/{alias}/validity")
    public CheckValidityResponse validity(@PathVariable String alias) {
        // TODO: Maybe here we want to keep some mapping between alias and certificate's serial number
        var certificate = certificatesService.read(alias);
        return toCheckValidityResponse.convert(certificate);
    }

}
