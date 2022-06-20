package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "devices")
public class Device extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "regex_filter", nullable = false)
    private String regexFilter;

    @Column(name = "send_rate", nullable = false)
    private Integer sendRate;

    @ManyToOne
    private RealEstate realEstate;

    public Device() {
        super();
    }

    public Device(String name, String regexFilter, Integer sendRate) {
        this();
        this.name = name;
        this.regexFilter = regexFilter;
        this.sendRate = sendRate;
    }
}
