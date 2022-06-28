package com.example.demo.service.impl;

import com.example.demo.dto.SearchDeviceReport;
import com.example.demo.model.Device;
import com.example.demo.model.RealEstate;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.service.DeviceService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserService userService;

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

    @Override
    public Page<Device> read(SearchDeviceReport report, Pageable pageable){
        report.setRegex("%" + report.getRegex().toLowerCase(Locale.ROOT) + "%");
        return deviceRepository.getAllPageable(report.getRegex(), pageable);
    }

    @Override
    public List<Device> read(String username){
        return deviceRepository.getAllByRealEstateIn(userService.getMyRealEstates(username));
    }
}
