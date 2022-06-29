package kiber.bezbednjaci.dto.auth;

import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {
    private String name;
    private String surname;
    private String email;
    private String accessToken;
    List<Integer> realEstates;
}
