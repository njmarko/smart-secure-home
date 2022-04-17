package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "certificate_data")
public class CertificateData extends BaseEntity {

    @Column(name = "alias", nullable = false, unique = true)
    private String alias;

    @OneToOne
    @JoinColumn(name = "revocation_id", referencedColumnName = "id")
    private Revocation revocation;

    public CertificateData() {
        super();
    }

    public CertificateData(String alias) {
        this();
        this.alias = alias;
    }

    public CertificateData(String alias, Revocation revocation) {
        this(alias);
        this.revocation = revocation;
    }
}
