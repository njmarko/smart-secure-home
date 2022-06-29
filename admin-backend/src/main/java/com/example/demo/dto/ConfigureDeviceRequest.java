package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class ConfigureDeviceRequest {
    @NotBlank(message = "Name is required.")
    @Pattern(regexp = "^(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")
    private String name;

    @NotBlank(message = "Regex filter is required.")
    private String regexFilter;

    @NotNull(message = "Send rate is required.")
    @Positive(message = "Send rate must be a positive number.")
    private Integer sendRate;
}
