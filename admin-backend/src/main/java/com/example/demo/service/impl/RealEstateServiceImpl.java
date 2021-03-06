package com.example.demo.service.impl;

import java.util.List;
import java.util.Objects;

import com.example.demo.dto.CreateRealEstateRequest;
import com.example.demo.exception.RealEstateNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Device;
import com.example.demo.model.RealEstate;
import com.example.demo.model.User;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.repository.RealEstateRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RealEstateService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RealEstateServiceImpl implements RealEstateService {
    private final RealEstateRepository realEstateRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    @Override
    @Transactional
    public RealEstate create(CreateRealEstateRequest request) {
        RealEstate realEstate = new RealEstate(request.getName());
        request.getStakeholders().stream().filter(Objects::nonNull).forEach(id -> {
            User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
            user.addRealEstate(realEstate);
            realEstate.addStakeholder(user);
        });
        return realEstateRepository.save(realEstate);
    }

    @Override
    public RealEstate read(Integer id) {
        return realEstateRepository.findById(id).orElseThrow(RealEstateNotFoundException::new);
    }

    @Override
    public List<RealEstate> read() {
        return realEstateRepository.read();
    }

    @Override
    @Transactional
    public List<Device> readDevicesFor(Integer id) {
        return deviceRepository.getForRealEstate(id);
    }
}
