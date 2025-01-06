package com.suseok.run.model.service;

public interface EmailVerificationService {

    String sendVerificationCode(String email);

    String verifyCode(String email, String code);

    String resendVerificationCode(String email);
}
