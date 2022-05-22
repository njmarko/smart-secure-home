package com.example.demo.service;

import com.example.demo.dto.CreateRealEstateRequest;
import com.example.demo.model.RealEstate;

public interface RealEstateService {
    RealEstate create(CreateRealEstateRequest request);
}
