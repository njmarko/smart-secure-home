package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CSRPurpose {
    ADVANCED_USER,
    STANDARD_USER,
    DEVICE;

    // za serijalizaciju
    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
