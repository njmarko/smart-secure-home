package com.example.demo.service;

import com.example.demo.dto.CreateRealEstateRequest;
import com.example.demo.model.RealEstate;

import java.util.List;

public interface RealEstateService {
    RealEstate create(CreateRealEstateRequest request);

    List<RealEstate> read();
}
