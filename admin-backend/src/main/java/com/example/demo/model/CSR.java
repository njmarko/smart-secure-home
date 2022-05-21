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
    @Column
    private String country;
    @Column
    private String email;
    @Column
    private String state;
    @Column
    private String commonName;
    @Column
    private String organization;
    @Column
    private String organizationUnit;
    @Column
    private String locale;
    @Column
    private CSRPurpose purpose;
    @Column(length = 20000)
    private String pemCSR;
    @Column
    private Boolean verified;

    public CSR() {
        super();
        this.verified = false;
    }

    @Version
    private Integer version;

}
