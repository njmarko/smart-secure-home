package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.Device;
import com.example.demo.model.RealEstate;
import com.example.demo.service.DeviceService;
import com.example.demo.service.RealEstateService;
import com.example.demo.support.EntityConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/real-estates")
@RequiredArgsConstructor
public class RealEstateController {
    private final RealEstateService realEstateService;
    private final DeviceService deviceService;

    private final EntityConverter<RealEstate, ReadRealEstateResponse> toResponse;
    private final EntityConverter<ConfigureDeviceRequest, Device> toDevice;

    private final ModelMapper mapper;

    @PreAuthorize("hasAuthority('CREATE_REAL_ESTATE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReadRealEstateResponse create(@Valid @RequestBody CreateRealEstateRequest request) {
        var realEstate = realEstateService.create(request);
        return toResponse.convert(realEstate);
    }

    @PreAuthorize("hasAuthority('ADD_REAL_ESTATE_TO_USER')")
    @GetMapping
    public List<ReadRealEstateResponse> read() {
        var realEstates = realEstateService.read();
        return toResponse.convert(realEstates);
    }

    @PreAuthorize("hasAuthority('READ_REAL_ESTATE_DEVICES')")
    @GetMapping("/{id}/devices")
    public List<ReadDeviceResponse> readRealEstateDevices(@PathVariable Integer id) {
        var devices = realEstateService.readDevicesFor(id);
        return devices.stream().map(d -> mapper.map(d, ReadDeviceResponse.class)).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('CONFIGURE_DEVICES')")
    @PostMapping(value = "/{id}/devices")
    @ResponseStatus(HttpStatus.CREATED)
    public ConfigureDeviceResponse configureDevice(@PathVariable Integer id, @Valid @RequestBody ConfigureDeviceRequest request) {
        var device = toDevice.convert(request);
        var realEstate = realEstateService.read(id);
        device = deviceService.configureDevice(device, realEstate);
        return mapper.map(device, ConfigureDeviceResponse.class);
    }
}
