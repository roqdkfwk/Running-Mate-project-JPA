package com.suseok.run.controller;

import com.suseok.run.common.auth.JwtUtil;
import com.suseok.run.model.entity.Request.LoginReq;
import com.suseok.run.model.entity.Response.LoginRes;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.service.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "AuthRestController", description = "인증/인가")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호가 틀렸습니다.")
    public ResponseEntity<User> login(@RequestBody LoginReq loginReq) {
        LoginRes loginRes = authService.login(loginReq);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", JwtUtil.TOKEN_PREFIX + loginRes.getAccessToken())
                .body(loginRes.getUser());
    }
}
