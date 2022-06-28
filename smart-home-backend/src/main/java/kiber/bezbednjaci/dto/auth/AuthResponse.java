package kiber.bezbednjaci.dto.auth;

import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {
    private String accessToken;
    List<Integer> realEstates;
}
