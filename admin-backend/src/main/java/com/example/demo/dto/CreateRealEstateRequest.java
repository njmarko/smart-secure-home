package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CreateRealEstateRequest {
    @NotBlank(message = "Name is required.")
    private String name;

    private List<Integer> stakeholders;
}
