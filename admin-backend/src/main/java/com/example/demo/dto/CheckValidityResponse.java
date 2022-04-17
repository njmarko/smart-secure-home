package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CheckValidityResponse {
    private Integer serialNumber;
    private Boolean valid;
    private Boolean expired;
    private Boolean started;
}
