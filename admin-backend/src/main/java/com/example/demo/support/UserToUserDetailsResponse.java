package com.example.demo.support;

import com.example.demo.dto.UserDetailsResponse;
import com.example.demo.model.BaseEntity;
import com.example.demo.model.User;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserToUserDetailsResponse extends BaseConverter<User, UserDetailsResponse> {
    @Override
    public UserDetailsResponse convert(@NonNull User source) {
        var details = new UserDetailsResponse();
        details.setId(source.getId());
        details.setFirstName(source.getFirstName());
        details.setLastName(source.getLastName());
        details.setEmail(source.getEmail());
        details.setUsername(source.getUsername());
        details.setRealEstates(source.getRealEstates().stream().map(BaseEntity::getId).collect(Collectors.toList()));
        return details;
    }
}
