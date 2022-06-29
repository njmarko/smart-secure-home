package com.example.demo.twofa.core.client;


import com.example.demo.twofa.core.RegistrationResult;
import com.example.demo.twofa.core.VerificationResult;
import com.example.demo.twofa.core.credentials.login.ILoginCredentials;
import com.example.demo.twofa.core.credentials.registration.IRegistrationCredentials;

public interface IFaceVerificationClient {
    VerificationResult verify(ILoginCredentials userCredentials);
    RegistrationResult register(IRegistrationCredentials userCredentials);
}
