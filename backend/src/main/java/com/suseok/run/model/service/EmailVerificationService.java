package com.suseok.run.model.service;

public interface EmailVerificationService {

    // Todo: 이메일 인증 코드 전송
    String sendVerificationCode(String email);

    // Todo: 이메일 인증 코드 검증
    String verifyCode(String email, String code);

    // Todo: 이메일 인증 코드 재전송
    String resendVerificationCode(String email);
}
