package com.suseok.run.model.service;

public interface EmailAuthService {

    // Todo: 인증 번호 생성
    String generateVerificationCode();

    // Todo: 인증 번호 발송
    void sendVerificationCode(String email);

    // Todo: 인증 번호 일치 여부 확인
    boolean verifyCode(String email, String code);

    // Todo: 이메일 인증 여부 확인
    void registerUser();
}
