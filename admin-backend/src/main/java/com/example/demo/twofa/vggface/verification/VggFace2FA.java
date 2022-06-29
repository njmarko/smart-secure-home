package com.example.demo.twofa.vggface.verification;


import com.example.demo.twofa.core.client.IFaceVerificationClient;
import com.example.demo.twofa.core.verification.impl.FaceVerification2FABase;
import com.example.demo.twofa.vggface.client.IVggFaceClient;

public class VggFace2FA extends FaceVerification2FABase {
    private final IVggFaceClient vggFaceClient;

    public VggFace2FA(IVggFaceClient vggFaceClient) {
        this.vggFaceClient = vggFaceClient;
    }

    @Override
    protected IFaceVerificationClient client() {
        return vggFaceClient;
    }
}
