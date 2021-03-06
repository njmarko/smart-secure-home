package com.example.demo.dto;

import lombok.Data;

@Data
public class ReadDeviceResponse {
    private Integer id;
    private Integer realEstateId;
    private String realEstateName;
    private String name;
    private String regexFilter;
    private Integer sendRate;
}
