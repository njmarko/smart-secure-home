package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data @Builder
public class ReadCertificateResponse {
    private Integer serialNumber;
    private X500PrincipalData issuer;
    private X500PrincipalData subject;
}
