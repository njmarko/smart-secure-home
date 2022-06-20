package com.example.demo.service.impl;

import com.example.demo.model.Device;
import com.example.demo.model.RealEstate;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    @Override
    @Transactional
    public Device configureDevice(Device device, RealEstate realEstate) {
        device.setRealEstate(realEstate);
        return deviceRepository.save(device);
    }
}
