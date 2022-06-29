package com.example.demo.twofa.core.credentials.registration;

import java.io.Serializable;
import java.util.Collection;

public interface IRegistrationCredentials extends Serializable {
    String getUsername();
    Collection<String> getImages();
}
