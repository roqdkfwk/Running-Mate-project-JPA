package com.suseok.run.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}
