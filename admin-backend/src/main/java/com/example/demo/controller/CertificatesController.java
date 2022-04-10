package com.example.demo.controller;


import com.example.demo.dto.ReadCertificateResponse;
import com.example.demo.service.CertificatesService;
import com.example.demo.support.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.cert.X509Certificate;
import java.util.List;

@RestController
@RequestMapping("api/certificates")
public class CertificatesController {
    private final CertificatesService certificatesService;

    private final EntityConverter<X509Certificate, ReadCertificateResponse> toReadResponse;

    @Autowired
    public CertificatesController(CertificatesService certificatesService, EntityConverter<X509Certificate, ReadCertificateResponse> toReadResponse) {
        this.certificatesService = certificatesService;
        this.toReadResponse = toReadResponse;
    }

    @GetMapping
    public List<ReadCertificateResponse> read() {
        var certificates = certificatesService.read();
        return toReadResponse.convert(certificates);
    }

}
