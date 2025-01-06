package com.suseok.run.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailVerification {
    private String email;
    private String verificationCode;
    private LocalDateTime expiresAt;
}
