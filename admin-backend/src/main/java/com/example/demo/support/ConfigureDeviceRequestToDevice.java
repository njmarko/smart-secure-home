package com.example.demo.support;

import com.example.demo.dto.ConfigureDeviceRequest;
import com.example.demo.model.Device;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ConfigureDeviceRequestToDevice extends BaseConverter<ConfigureDeviceRequest, Device> {
    @Override
    public Device convert(@NonNull ConfigureDeviceRequest source) {
        return getModelMapper().map(source, Device.class);
    }
}
