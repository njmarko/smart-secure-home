package com.example.demo.service.impl;

import com.example.demo.model.Device;
import com.example.demo.model.RealEstate;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

    @Override
    public Device read(Integer id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find device with given id.");
        }
        return deviceOptional.get();
    }
}
