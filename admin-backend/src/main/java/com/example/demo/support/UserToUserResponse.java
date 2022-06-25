package com.example.demo.support;

import com.example.demo.dto.UserResponse;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponse extends BaseConverter<User, UserResponse> {
    @Override
    public UserResponse convert(@NonNull User source) {
        UserResponse dto = getModelMapper().map(source, UserResponse.class);
        dto.setRole(source.getRoles().stream().map(Role::getName).findFirst().orElse("ROLE_UNKNOWN"));
        return dto;
    }
}
