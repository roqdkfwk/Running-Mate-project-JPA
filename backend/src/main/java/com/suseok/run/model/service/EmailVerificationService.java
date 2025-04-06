package com.suseok.run.model.service;

public interface EmailVerificationService {

    String checkEmailDuplication(String email);

    String verifyCode(String email, String code);

    String resendVerificationCode(String email);
}
