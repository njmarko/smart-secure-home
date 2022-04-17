package com.example.demo.dto;

import com.example.demo.model.RevocationReason;
import lombok.Data;

@Data
public class InvalidateCertificateRequest {
    private RevocationReason reason;
}
