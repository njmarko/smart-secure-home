package com.example.demo.service;

import com.example.demo.dto.CreateRealEstateRequest;
import com.example.demo.model.Device;
import com.example.demo.model.RealEstate;

import java.util.List;

public interface RealEstateService {
    RealEstate create(CreateRealEstateRequest request);

    RealEstate read(Integer id);

    List<RealEstate> read();

    List<Device> readDevicesFor(Integer id);
}
