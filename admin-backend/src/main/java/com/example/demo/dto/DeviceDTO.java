package com.example.demo.dto;

import com.example.demo.model.Device;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceDTO {
    private String realEstateName;
    private String name;
    private Integer deviceId;
    private Integer estateId;

    public DeviceDTO(Device device){
        this.name = device.getName();
        this.realEstateName = device.getRealEstate().getName();
        this.deviceId = device.getId();
        this.estateId = device.getRealEstate().getId();
    }
}
