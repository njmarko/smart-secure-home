package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PrivateKey;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssuerData {

    private X500Name x500name;
    private PrivateKey privateKey;

}
