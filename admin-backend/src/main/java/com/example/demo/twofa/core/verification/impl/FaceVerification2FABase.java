package com.example.demo.twofa.core.verification.impl;

import com.example.demo.twofa.core.RegistrationResult;
import com.example.demo.twofa.core.VerificationResult;
import com.example.demo.twofa.core.client.IFaceVerificationClient;
import com.example.demo.twofa.core.credentials.login.ILoginCredentials;
import com.example.demo.twofa.core.credentials.registration.IRegistrationCredentials;
import com.example.demo.twofa.core.verification.IFaceVerification2FA;

public abstract class FaceVerification2FABase implements IFaceVerification2FA {
    protected abstract IFaceVerificationClient client();

    @Override
    public VerificationResult verify(ILoginCredentials credentials) {
        return client().verify(credentials);
    }

    @Override
    public RegistrationResult register(IRegistrationCredentials credentials) {
        return client().register(credentials);
    }
}
