package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class X500PrincipalData {
    private String country;
    private String email;
    private String state;
    private String commonName;
    private String organization;
    private String organizationUnit;
}
