package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "revocation")
public class Revocation extends BaseEntity {

    @OneToOne(mappedBy = "revocation")
    private CertificateData certificateData;

    @Column(nullable = false, length = 1000)
    private String reason;

    public Revocation() {
        super();
    }

    public Revocation(CertificateData certificateData, String reason) {
        this();
        this.certificateData = certificateData;
        this.reason = reason;
    }
}
