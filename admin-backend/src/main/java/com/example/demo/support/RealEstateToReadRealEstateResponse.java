package com.example.demo.support;

import com.example.demo.dto.ReadRealEstateResponse;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.RealEstate;
import com.example.demo.model.User;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;
@Component
public class RealEstateToReadRealEstateResponse extends BaseConverter<RealEstate, ReadRealEstateResponse> {
    @Override
    public ReadRealEstateResponse convert(@NonNull RealEstate source) {
        var readRealEstateResponse = new ReadRealEstateResponse();
        readRealEstateResponse.setId(source.getId());
        readRealEstateResponse.setName(source.getName());
        readRealEstateResponse.setStakeholders(source.getStakeholders().stream().map(new Function<User, UserResponse>() {
            @Override
            public UserResponse apply(User user) {
                var userResponse = new UserResponse();
                userResponse.setId(user.getId());
                userResponse.setEmail(user.getEmail());
                userResponse.setUsername(user.getUsername());
                userResponse.setFirstName(user.getFirstName());
                userResponse.setLastName(user.getLastName());
                userResponse.setRole(user.getRoles().get(0).getName());
                return userResponse;
            }
        }).collect(Collectors.toSet()));
        return readRealEstateResponse;
    }
}
