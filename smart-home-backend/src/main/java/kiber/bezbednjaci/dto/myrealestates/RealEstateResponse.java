package kiber.bezbednjaci.dto.myrealestates;

import lombok.Data;

import java.util.Set;

@Data
public class RealEstateResponse {
    private Integer id;
    private String name;
    private Set<UserResponse> stakeholders;
}
