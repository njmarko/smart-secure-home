package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data @Builder
public class CheckValidityResponse {
    private BigInteger serialNumber;
    private Boolean valid;
    private Boolean expired;
    private Boolean started;
}
