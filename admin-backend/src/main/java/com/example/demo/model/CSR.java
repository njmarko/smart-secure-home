package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

@Getter
@Setter
@Entity
@ToString
@Table(name = "csr")
public class CSR extends BaseEntity {

    private String country;
    private String email;
    private String state;
    private String commonName;
    private String organization;
    private String organizationUnit;
    private String locale;
    private CSRPurpose purpose;
    @Column(length = 20000)
    private String pemCSR;

    @Version
    private Integer version;

}
