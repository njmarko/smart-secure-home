package com.example.demo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class X500PrincipalData {
    private String country;
    private String email;
    private String state;
    private String commonName;
    private String organization;
    private String organizationUnit;
    private String locale;
    private String x500;
}
