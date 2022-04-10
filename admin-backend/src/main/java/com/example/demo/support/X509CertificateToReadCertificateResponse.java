package com.example.demo.support;

import com.example.demo.dto.ReadCertificateResponse;
import org.springframework.stereotype.Component;

import java.security.cert.X509Certificate;

@Component
public class X509CertificateToReadCertificateResponse extends BaseConverter<X509Certificate, ReadCertificateResponse> {
    @Override
    public ReadCertificateResponse convert(X509Certificate source) {
        return ReadCertificateResponse.builder()
                .sertialNumber(source.getSerialNumber())
                .build();
    }
}
