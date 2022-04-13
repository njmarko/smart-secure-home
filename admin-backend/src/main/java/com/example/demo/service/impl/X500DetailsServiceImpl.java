package com.example.demo.service.impl;

import com.example.demo.service.X500DetailsService;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Service;

@Service
public class X500DetailsServiceImpl implements X500DetailsService {

    @Override
    public String getX500Field(String asn1ObjectIdentifier, X500Name x500Name) {
        RDN[] rdnArray = x500Name.getRDNs(new ASN1ObjectIdentifier(asn1ObjectIdentifier));
        String retVal = null;
        for (RDN item : rdnArray) {
            retVal = item.getFirst().getValue().toString();
        }
        return retVal;
    }

    @Override
    public String getCountry(X500Name x500Name) {
        return getX500Field(COUNTRY, x500Name);
    }

    @Override
    public String getState(X500Name x500Name) {
        return getX500Field(STATE, x500Name);
    }

    @Override
    public String getLocale(X500Name x500Name) {
        return getX500Field(LOCALE, x500Name);
    }

    @Override
    public String getOrganizationUnit(X500Name x500Name) {
        return getX500Field(ORGANIZATION_UNIT, x500Name);
    }

    @Override
    public String getOrganization(X500Name x500Name) {
        return getX500Field(ORGANIZATION, x500Name);
    }

    @Override
    public String getEmail(X500Name x500Name) {
        return getX500Field(EMAIL, x500Name);
    }

    @Override
    public String getCommonName(X500Name x500Name) {
        return getX500Field(COMMON_NAME, x500Name);
    }
}
