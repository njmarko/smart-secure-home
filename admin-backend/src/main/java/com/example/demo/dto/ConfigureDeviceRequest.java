package com.example.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class ConfigureDeviceRequest {
    @NotBlank(message = "Name is required.")
    @Length(max = 100, message = "Name cannot be longer than 100 characters.")
    @Pattern(regexp = "^(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")
    private String name;

    @NotBlank(message = "Regex filter is required.")
    @Length(max = 200, message = "Name cannot be longer than 100 characters.")
    private String regexFilter;

    @NotNull(message = "Send rate is required.")
    @Positive(message = "Send rate must be a positive number.")
    private Integer sendRate;
}
