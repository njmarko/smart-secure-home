package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigInteger;

@Getter
@Setter
@Entity
@Table(name = "certificate_data", indexes = {
    @Index(name = "serial_number_index", columnList = "serial_number", unique = true)
})
public class CertificateData extends BaseEntity {

    @Column(name = "alias", nullable = false, unique = true)
    private String alias;

    @Column(name = "serial_number", nullable = false, unique = true)
    private BigInteger serialNumber;

    @Column(name = "is_cancelled")
    private Boolean isCancelled = false;

    public CertificateData() {
    }

    public CertificateData(String alias, BigInteger serialNumber, Boolean isCancelled) {
        super();
        this.alias = alias;
        this.serialNumber = serialNumber;
        this.isCancelled = isCancelled;
    }
}
