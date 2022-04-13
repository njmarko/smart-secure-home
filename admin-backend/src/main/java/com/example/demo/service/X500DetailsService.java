package com.example.demo.service;

import org.bouncycastle.asn1.x500.X500Name;

public interface X500DetailsService {
    String COUNTRY = "2.5.4.6";
    String STATE = "2.5.4.8";
    String LOCALE = "2.5.4.7";
    String ORGANIZATION = "2.5.4.10";
    String ORGANIZATION_UNIT = "2.5.4.11";
    String COMMON_NAME = "2.5.4.3";
    String EMAIL = "2.5.4.9";

    String getX500Field(String asn1ObjectIdentifier, X500Name x500Name);

    String getCountry(X500Name x500Name);

    String getState(X500Name x500Name);

    String getLocale(X500Name x500Name);

    String getOrganizationUnit(X500Name x500Name);

    String getOrganization(X500Name x500Name);

    String getEmail(X500Name x500Name);

    String getCommonName(X500Name x500Name);
}
