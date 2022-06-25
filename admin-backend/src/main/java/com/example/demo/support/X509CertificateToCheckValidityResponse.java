package com.example.demo.support;

import com.example.demo.dto.CheckValidityResponse;
import org.springframework.stereotype.Component;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

@Component
public class X509CertificateToCheckValidityResponse extends BaseConverter<X509Certificate, CheckValidityResponse> {
    @Override
    public CheckValidityResponse convert(X509Certificate source) {
        CheckValidityResponse.CheckValidityResponseBuilder builder = CheckValidityResponse.builder()
                .serialNumber(source.getSerialNumber().intValue())
                .expired(false)
                .started(true);
        try {
            source.checkValidity();
            builder.valid(true);
        } catch (CertificateExpiredException e) {
            builder.expired(true);
        } catch (CertificateNotYetValidException e) {
            builder.started(false);
        }
        return builder.build();
    }
}
