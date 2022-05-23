package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailsResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private List<Integer> realEstates;
}
