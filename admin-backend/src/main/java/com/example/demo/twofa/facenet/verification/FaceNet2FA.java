package com.example.demo.twofa.facenet.verification;

import com.example.demo.twofa.core.client.IFaceVerificationClient;
import com.example.demo.twofa.core.verification.impl.FaceVerification2FABase;
import com.example.demo.twofa.facenet.client.IFaceNetClient;

public class FaceNet2FA extends FaceVerification2FABase {
    private final IFaceNetClient faceNetClient;

    public FaceNet2FA(IFaceNetClient faceNetClient) {
        this.faceNetClient = faceNetClient;
    }

    @Override
    protected IFaceVerificationClient client() {
        return faceNetClient;
    }
}
