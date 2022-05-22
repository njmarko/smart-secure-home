package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "blacklisted_token")
public class BlacklistedToken extends BaseEntity {
    @Column(name = "token", nullable = false, unique = true, length = 1000)
    private String token;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    public BlacklistedToken() {
        super();
    }

    public BlacklistedToken(String token) {
        this();
        this.token = token;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }
}
