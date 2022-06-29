package com.example.demo.twofa.facenet.config;


import com.example.demo.twofa.core.config.FaceVerificationClientFallbackConfigBase;
import com.example.demo.twofa.facenet.client.fallback.FaceNetClientFallback;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FaceNetClientConfig extends FaceVerificationClientFallbackConfigBase {
    public FaceNetClientConfig(CircuitBreakerRegistry registry, FaceNetClientFallback fallbackClient) {
        super(registry, fallbackClient, "face-net-verification");
    }
}
