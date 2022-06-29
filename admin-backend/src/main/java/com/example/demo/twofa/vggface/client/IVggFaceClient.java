package com.example.demo.twofa.vggface.client;

import com.example.demo.twofa.core.RegistrationResult;
import com.example.demo.twofa.core.VerificationResult;
import com.example.demo.twofa.core.client.IFaceVerificationClient;
import com.example.demo.twofa.core.credentials.login.ILoginCredentials;
import com.example.demo.twofa.core.credentials.registration.IRegistrationCredentials;
import com.example.demo.twofa.vggface.config.VggFaceClientConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name = "vgg-face-verification")
// TODO: Ako treba da se pokrene vggface ovo skloniti i dodati u FaceNetClient! Ovo je samo quick fix :D
@FeignClient(value = "face-verification-service-vggface", qualifiers = "vgg-face-client", configuration = VggFaceClientConfig.class)
public interface IVggFaceClient extends IFaceVerificationClient {
    @PostMapping(value = "/vggface")
    VerificationResult verify(@RequestBody ILoginCredentials userCredentials);

    @PostMapping(value = "/register/vggface")
    RegistrationResult register(@RequestBody IRegistrationCredentials userCredentials);
}
