package kiber.bezbednjaci.controller;

import kiber.bezbednjaci.dto.auth.AuthRequest;
import kiber.bezbednjaci.dto.auth.AuthResponse;
import kiber.bezbednjaci.dto.myrealestates.RealEstateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final RestTemplate restTemplate;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        return restTemplate.postForEntity("http://localhost:8082/api/auth/login", authRequest, AuthResponse.class);
    }

    @GetMapping("/my-real-estates")
    public List<RealEstateResponse> myRealEstates() {
        var realEstates = restTemplate.getForObject("http://localhost:8082/api/users/my-real-estates", RealEstateResponse[].class);
        if (Objects.isNull(realEstates)) {
            return List.of();
        }
        return Arrays.asList(realEstates);
    }
}
