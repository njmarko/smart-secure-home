package com.example.demo.support;

import com.example.demo.dto.ReadCertificateResponse;
import com.example.demo.dto.X500PrincipalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.x500.X500Principal;
import java.security.cert.X509Certificate;

@Component
public class X509CertificateToReadCertificateResponse extends BaseConverter<X509Certificate, ReadCertificateResponse> {
    private final EntityConverter<X500Principal, X500PrincipalData> toData;

    @Autowired
    public X509CertificateToReadCertificateResponse(EntityConverter<X500Principal, X500PrincipalData> toData) {
        this.toData = toData;
    }

    @Override
    public ReadCertificateResponse convert(X509Certificate source) {
        return ReadCertificateResponse.builder()
                .sertialNumber(source.getSerialNumber())
                .issuer(toData.convert(source.getIssuerX500Principal()))
                .subject(toData.convert(source.getSubjectX500Principal()))
                .build();
    }
}
