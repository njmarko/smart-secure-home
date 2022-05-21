package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="real_estates")
public class RealEstate extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "realEstates")
    private Set<User> stakeholders = new HashSet<>();

    public RealEstate() {
        super();
    }
}
