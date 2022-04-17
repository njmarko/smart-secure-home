package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "revocation")
public class Revocation extends BaseEntity {

    @OneToOne(mappedBy = "revocation")
    private CertificateData certificateData;

    @Column(nullable = false)
    private Date revocationTime;

    @Column(nullable = false)
    private RevocationReason reason;

    public Revocation() {
        super();
    }

    public Revocation(CertificateData certificateData, Date revocationTime, RevocationReason reason) {
        this();
        this.certificateData = certificateData;
        this.revocationTime = revocationTime;
        this.reason = reason;
    }
}
