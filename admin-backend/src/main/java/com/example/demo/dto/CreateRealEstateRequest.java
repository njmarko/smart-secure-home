package com.example.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class CreateRealEstateRequest {
    @NotBlank(message = "Name is required.")
    @Length(max = 100, message = "Name cannot be longer than 100 characters.")
    @Pattern(regexp = "^(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")
    private String name;

    @NotNull(message = "Stakeholders are required.")
    private List<Integer> stakeholders;
}
