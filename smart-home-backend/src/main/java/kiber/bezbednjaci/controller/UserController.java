package kiber.bezbednjaci.controller;

import kiber.bezbednjaci.dto.auth.AuthRequest;
import kiber.bezbednjaci.dto.auth.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final RestTemplate restTemplate;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        return restTemplate.postForEntity("http://localhost:8082/api/auth/login", authRequest, AuthResponse.class);
    }
}
