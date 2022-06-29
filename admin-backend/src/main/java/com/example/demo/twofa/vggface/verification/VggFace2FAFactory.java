package com.example.demo.twofa.vggface.verification;


import com.example.demo.twofa.core.verification.IFaceVerification2FA;
import com.example.demo.twofa.core.verification.IFaceVerification2FAFactory;
import com.example.demo.twofa.vggface.client.IVggFaceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(value = "vgg-face-factory")
public class VggFace2FAFactory implements IFaceVerification2FAFactory {
    private final IVggFaceClient vggFaceClient;

    @Autowired
    public VggFace2FAFactory(@Qualifier("vgg-face-client") IVggFaceClient vggFaceClient) {
        this.vggFaceClient = vggFaceClient;
    }

    @Override
    public IFaceVerification2FA create2FA() {
        return new VggFace2FA(vggFaceClient);
    }
}
