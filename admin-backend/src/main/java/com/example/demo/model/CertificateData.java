package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToOne
    @JoinColumn(name = "revocation_id", referencedColumnName = "id")
    private Revocation revocation;

    public CertificateData() {
        super();
    }

    public CertificateData(String alias, BigInteger serialNumber) {
        this();
        this.alias = alias;
        this.serialNumber = serialNumber;
    }

    public CertificateData(String alias, BigInteger serialNumber, Revocation revocation) {
        this(alias, serialNumber);
        this.revocation = revocation;
    }
}
