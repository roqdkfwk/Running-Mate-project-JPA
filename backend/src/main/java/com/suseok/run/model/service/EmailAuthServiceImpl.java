package com.suseok.run.model.service;

import org.springframework.stereotype.Service;

@Service
public class EmailAuthServiceImpl implements EmailAuthService {

    @Override
    public String generateVerificationCode() {
        return "";
    }

    @Override
    public void sendVerificationCode(String email) {

    }

    @Override
    public boolean verifyCode(String email, String code) {
        return false;
    }

    @Override
    public void registerUser() {

    }
}
