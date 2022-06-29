package com.example.demo.twofa.core.credentials.registration.impl;

import com.example.demo.twofa.core.credentials.registration.IRegistrationCredentials;

import java.util.Arrays;
import java.util.Collection;

public class RegistrationCredentials implements IRegistrationCredentials {
    @Override
    public String getUsername() {
        return "dusan";
    }

    @Override
    public Collection<String> getImages() {
        return Arrays.asList("slika1", "slika2", "slika3");
    }
}
