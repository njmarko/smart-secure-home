package com.example.demo.support;

import com.example.demo.dto.CertificateExtensions;
import com.example.demo.dto.ReadCertificateResponse;
import com.example.demo.dto.X500PrincipalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.x500.X500Principal;
import java.security.cert.X509Certificate;

@Component
public class X509CertificateToReadCertificateResponse extends BaseConverter<X509Certificate, ReadCertificateResponse> {
    private final EntityConverter<X500Principal, X500PrincipalData> toData;
    private final EntityConverter<X509Certificate, CertificateExtensions> toExtensions;

    @Autowired
    public X509CertificateToReadCertificateResponse(EntityConverter<X500Principal, X500PrincipalData> toData, EntityConverter<X509Certificate, CertificateExtensions> toExtensions) {
        this.toData = toData;
        this.toExtensions = toExtensions;
    }

    @Override
    public ReadCertificateResponse convert(X509Certificate source) {
        return ReadCertificateResponse.builder()
                .serialNumber(source.getSerialNumber().intValue())
                .issuer(toData.convert(source.getIssuerX500Principal()))
                .subject(toData.convert(source.getSubjectX500Principal()))
                .extensions(toExtensions.convert(source))
                .build();
    }
}
