package com.example.demo.support;

import com.example.demo.dto.X500PrincipalData;
import com.example.demo.service.X500DetailsService;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.x500.X500Principal;

@Component
public class X500PrincipalToX500PrincipalData extends BaseConverter<X500Principal, X500PrincipalData> {
    private final X500DetailsService detailsService;

    @Autowired
    public X500PrincipalToX500PrincipalData(X500DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @Override
    public X500PrincipalData convert(X500Principal source) {
        X500Name x500Name = new X500Name(source.getName());
        return X500PrincipalData.builder()
                .email(detailsService.getEmail(x500Name))
                .commonName(detailsService.getCommonName(x500Name))
                .country(detailsService.getCountry(x500Name))
                .state(detailsService.getState(x500Name))
                .organization(detailsService.getOrganization(x500Name))
                .organizationUnit(detailsService.getOrganizationUnit(x500Name))
                .locale(detailsService.getLocale(x500Name))
                .x500(x500Name.toString())
                .build();
    }
}
