package com.example.demo.dto;

import lombok.Data;

@Data
public class CertificateExtensions {
    private Boolean isCA = false;
    private Boolean certificateSigning = false;
    private Boolean crlSigning = false;
    private Boolean digitalSignature = false;
    private Boolean keyEnchiperment = false;
    private Boolean serverAuth = false;
    private Boolean clientAuth = false;
    private Boolean codeSigning = false;
}
