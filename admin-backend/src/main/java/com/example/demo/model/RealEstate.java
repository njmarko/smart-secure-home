package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="real_estates")
public class RealEstate extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(
            mappedBy = "realEstates",
            fetch = FetchType.EAGER
    )
    private Set<User> stakeholders = new HashSet<>();

    @OneToMany(mappedBy = "realEstate")
    private Set<Device> devices = new HashSet<>();

    public RealEstate() {
        super();
    }

    public RealEstate(String name) {
        this();
        this.name = name;
    }

    public RealEstate addDevice(Device device) {
        this.devices.add(device);
        device.setRealEstate(this);
        return this;
    }

    public RealEstate addStakeholder(User user) {
        stakeholders.add(user);
        return this;
    }
}
