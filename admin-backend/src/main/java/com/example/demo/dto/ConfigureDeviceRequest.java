package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ConfigureDeviceRequest {
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Regex filter is required.")
    private String regexFilter;

    @NotNull(message = "Send rate is required.")
    @Positive(message = "Send rate must be a positive number.")
    private Integer sendRate;
}
