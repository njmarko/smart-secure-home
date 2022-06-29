package com.example.demo.twofa.vggface.client.fallback;

import com.example.demo.twofa.core.RegistrationResult;
import com.example.demo.twofa.core.VerificationResult;
import com.example.demo.twofa.core.client.fallback.FaceVerificationClientFallbackBase;
import com.example.demo.twofa.core.credentials.login.ILoginCredentials;
import com.example.demo.twofa.core.credentials.registration.IRegistrationCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VggFaceClientFallback extends FaceVerificationClientFallbackBase {
    @Override
    public VerificationResult verify(ILoginCredentials userCredentials) {
        log.info("vgg face fallback called");
        return super.verify(userCredentials);
    }

    @Override
    public RegistrationResult register(IRegistrationCredentials userCredentials) {
        log.info("vgg face fallback called");
        return super.register(userCredentials);
    }
}
