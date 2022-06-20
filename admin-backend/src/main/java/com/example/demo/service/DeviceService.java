package com.example.demo.service;

import com.example.demo.model.Device;
import com.example.demo.model.RealEstate;

public interface DeviceService {
    Device configureDevice(Device device, RealEstate realEstate);
}
