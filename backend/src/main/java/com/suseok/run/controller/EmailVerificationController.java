package com.suseok.run.controller;

import com.suseok.run.model.entity.Request.VerifyReq;
import com.suseok.run.model.service.EmailVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email-verification")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    /**
     * 이메일 인증번호 전송
     */
    @Operation(summary = "이메일 인증번호 전송", description = "사용자의 이메일 주소를 입력받아 인증번호를 전송합니다.")
    @PostMapping
    public ResponseEntity<String> sendVerificationCode(
            @Parameter(description = "이메일 주소를 입력하세요", required = true)
            @RequestParam String email
    ) {
        return ResponseEntity.ok(emailVerificationService.checkEmailDuplication(email));
    };

    /**
     * 이메일 인증번호 검증
     */
    @Operation(summary = "이메일 인증번호 검증", description = "입력한 인증번호를 검증합니다.")
    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(
            @RequestBody VerifyReq verifyReq
    ) {
        return ResponseEntity.ok(emailVerificationService.verifyCode(verifyReq.getEmail(), verifyReq.getCode()));
    }

    /**
     * 이메일 인증번호 재전송
     */
    @Operation(summary = "이메일 인증번호 재전송", description = "인증번호를 재전송합니다.")
    @PostMapping("/resend")
    public ResponseEntity<String> resendVerificationCode(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(emailVerificationService.resendVerificationCode(email));
    }
}