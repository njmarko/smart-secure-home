package com.example.demo.twofa.core.client.fallback;

import com.example.demo.twofa.core.RegistrationResult;
import com.example.demo.twofa.core.VerificationResult;
import com.example.demo.twofa.core.credentials.login.ILoginCredentials;
import com.example.demo.twofa.core.credentials.registration.IRegistrationCredentials;
import com.example.demo.twofa.facenet.client.IFaceNetClient;

public abstract class FaceVerificationClientFallbackBase implements IFaceNetClient {
    @Override
    public VerificationResult verify(ILoginCredentials userCredentials) {
        return VerificationResult.failure();
    }

    @Override
    public RegistrationResult register(IRegistrationCredentials userCredentials) {
        return RegistrationResult.failure();
    }
}
