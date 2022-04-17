package com.example.demo.support;

import com.example.demo.dto.CertificateExtensions;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.springframework.stereotype.Component;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Objects;

@Component
public class X509CertificateToCertificateExtensions extends BaseConverter<X509Certificate, CertificateExtensions> {
    @Override
    public CertificateExtensions convert(X509Certificate source) {
        var extensions = new CertificateExtensions();
        // Basic constraints
        if (source.getBasicConstraints() != -1) {
            extensions.setIsCA(true);
        }
        // Key usage
        if (!Objects.isNull(source.getKeyUsage())) {
            System.out.println(Arrays.toString(source.getKeyUsage()));
            var usageArray = source.getKeyUsage();
            extensions.setCertificateSigning(usageArray[log2(KeyUsage.keyCertSign)]);
            extensions.setCrlSigning(usageArray[log2(KeyUsage.cRLSign)]);
            extensions.setDigitalSignature(usageArray[log2(KeyUsage.digitalSignature)]);
            extensions.setKeyEnchiperment(usageArray[log2(KeyUsage.keyEncipherment)]);
        }
        // Extended key usage
        try {
            if (!Objects.isNull(source.getExtendedKeyUsage())) {
                var usageList = source.getExtendedKeyUsage();
                for (var item: usageList) {
                    if (item.equals(KeyPurposeId.id_kp_serverAuth.toString())) {
                        extensions.setServerAuth(true);
                    } else if (item.equals(KeyPurposeId.id_kp_clientAuth.toString())) {
                        extensions.setClientAuth(true);
                    } else if (item.equals(KeyPurposeId.id_kp_codeSigning.toString())) {
                        extensions.setCodeSigning(true);
                    }
                }
            }
        } catch (CertificateParsingException e) {
            e.printStackTrace();
        }
        return extensions;
    }

    private int log2(int N) {
        return (int)(Math.log(N) / Math.log(2));
    }
}
