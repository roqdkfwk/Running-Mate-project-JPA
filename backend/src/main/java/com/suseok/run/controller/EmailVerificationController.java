package com.suseok.run.controller;

import com.suseok.run.model.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email-verification")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    /**
     * 이메일 인증 코드 전송
     */
    @PostMapping
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        return ResponseEntity.ok(emailVerificationService.sendVerificationCode(email));
    }

    /**
     * 이메일 인증 코드 검증
     */
    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestParam String email, @RequestParam String code) {
        return ResponseEntity.ok(emailVerificationService.verifyCode(email, code));
    }

    /**
     * 이메일 인증 코드 재전송
     */
    @PostMapping("/resend")
    public ResponseEntity<String> resendVerificationCode(@RequestParam String email) {
        return ResponseEntity.ok(emailVerificationService.resendVerificationCode(email));
    }
}
