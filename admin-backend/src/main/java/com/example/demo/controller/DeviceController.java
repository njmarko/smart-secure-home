package com.example.demo.controller;

import com.example.demo.dto.ReadDeviceResponse;
import com.example.demo.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;
    private final ModelMapper mapper;

    @GetMapping("/{id}")
    public ReadDeviceResponse read(@PathVariable Integer id) {
        var device = deviceService.read(id);
        return mapper.map(device, ReadDeviceResponse.class);
    }
}
