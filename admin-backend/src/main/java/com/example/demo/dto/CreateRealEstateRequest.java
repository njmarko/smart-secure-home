package com.example.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateRealEstateRequest {
    @NotBlank(message = "Name is required.")
    @Length(max = 100, message = "Name cannot be longer than 100 characters.")
    private String name;

    @NotNull(message = "Stakeholders are required.")
    private List<Integer> stakeholders;
}
