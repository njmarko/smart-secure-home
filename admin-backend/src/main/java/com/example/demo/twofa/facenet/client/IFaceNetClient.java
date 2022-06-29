package com.example.demo.twofa.facenet.client;

import com.example.demo.twofa.core.RegistrationResult;
import com.example.demo.twofa.core.VerificationResult;
import com.example.demo.twofa.core.client.IFaceVerificationClient;
import com.example.demo.twofa.core.credentials.login.ILoginCredentials;
import com.example.demo.twofa.core.credentials.registration.IRegistrationCredentials;
import com.example.demo.twofa.facenet.config.FaceNetClientConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name = "face-net-verification")
@FeignClient(value = "face-verification-service", qualifiers = "face-net-client", configuration = FaceNetClientConfig.class)
public interface IFaceNetClient extends IFaceVerificationClient {
    @PostMapping(value = "/facenet")
    VerificationResult verify(@RequestBody ILoginCredentials userCredentials);

    @PostMapping(value = "/register/facenet")
    RegistrationResult register(@RequestBody IRegistrationCredentials userCredentials);
}
