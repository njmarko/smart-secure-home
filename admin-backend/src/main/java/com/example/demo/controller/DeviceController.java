package com.example.demo.controller;

import com.example.demo.dto.DeviceDTO;
import com.example.demo.dto.ReadDeviceResponse;
import com.example.demo.dto.SearchDeviceReport;
import com.example.demo.model.Device;
import com.example.demo.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;
    private final ModelMapper mapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_REAL_ESTATE_DEVICES')")
    public ReadDeviceResponse read(@PathVariable Integer id) {
        var device = deviceService.read(id);
        return mapper.map(device, ReadDeviceResponse.class);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_REAL_ESTATE_DEVICES')")
    public List<DeviceDTO> read(Principal principal) {
        var devices = deviceService.read(principal.getName());
        return devices.stream().map(new Function<Device, DeviceDTO>() {
            @Override
            public DeviceDTO apply(Device device) {
                return new DeviceDTO(device);
            }
        }).collect(Collectors.toList());
    }
}
