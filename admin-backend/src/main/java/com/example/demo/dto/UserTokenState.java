package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// DTO koji enkapsulira generisani JWT i njegovo trajanje koji se vracaju klijentu
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenState {

    private String accessToken;
    private Long expiresIn;

    private String name;
    private String surname;
    private String email;

    List<String> authorities;

    public UserTokenState(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}