package com.example.demo.twofa.core.verification;

import com.example.demo.twofa.core.RegistrationResult;
import com.example.demo.twofa.core.VerificationResult;
import com.example.demo.twofa.core.credentials.login.ILoginCredentials;
import com.example.demo.twofa.core.credentials.registration.IRegistrationCredentials;

public interface IFaceVerification2FA {
    VerificationResult verify(ILoginCredentials credentials);

    RegistrationResult register(IRegistrationCredentials credentials);
}
