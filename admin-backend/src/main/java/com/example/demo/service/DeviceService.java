package com.example.demo.service;

import com.example.demo.dto.SearchDeviceReport;
import com.example.demo.model.Device;
import com.example.demo.model.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceService {
    Device configureDevice(Device device, RealEstate realEstate);

    Device read(Integer id);

    Page<Device> read(SearchDeviceReport report, Pageable pageable);
    List<Device> read(String name);
}
