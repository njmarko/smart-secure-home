package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "csr_verification_token")
public class CSRVerificationToken {
    private static final int EXPIRATION_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String token;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime expirationDate;

    @OneToOne(targetEntity = CSR.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "csr_id")
    private CSR csr;

    public CSRVerificationToken(CSR csr){
        this.csr = csr;
        this.createdDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
        this.setExpirationDate(this.createdDate.plusDays(EXPIRATION_DAYS));
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(this.getExpirationDate());
    }
}
