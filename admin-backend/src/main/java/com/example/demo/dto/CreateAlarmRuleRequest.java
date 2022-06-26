package com.example.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class CreateAlarmRuleRequest {
    @NotNull(message = "Name is required.")
    @Length(max = 2000)
    private String name;

    @NotNull(message = "When section is required.")
    @Length(max = 2000)
    private String when;

    @Length(max = 2000)
    private String then = "";

    @NotNull(message = "Message is required.")
    @Length(max = 2000)
    private String message;
}
