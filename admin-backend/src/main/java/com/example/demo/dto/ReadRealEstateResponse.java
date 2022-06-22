package com.example.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ReadRealEstateResponse {
    private Integer id;
    private String name;
    private Set<UserResponse> stakeholders;
}
