package com.example.demo.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String role;
}
