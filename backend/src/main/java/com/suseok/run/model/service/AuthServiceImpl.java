package com.suseok.run.model.service;

import com.suseok.run.common.auth.JwtUtil;
import com.suseok.run.common.exception.UnAuthorizedException;
import com.suseok.run.model.entity.Request.LoginReq;
import com.suseok.run.model.entity.Response.LoginRes;
import com.suseok.run.model.entity.Response.LoginResult;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final String ERROR_MESSAGE = "아이디 혹은 비밀번호가 일치하지 않습니다.";

    @Override
    public LoginResult login(LoginReq loginReq) {
        User user = userRepository.findByUserId(loginReq.getUserId()).orElseThrow(
                () -> new UnAuthorizedException(ERROR_MESSAGE)
        );

        if (!user.getUserPw().equals(loginReq.getUserPw())) {
            throw new UnAuthorizedException(ERROR_MESSAGE);
        }

        String accessToken = jwtUtil.generateAccessToken(user);
//        String refreshToken = jwtUtil.generateRefreshToken(user.getUserSeq());

        return new LoginResult(LoginRes.fromEntity(user), accessToken);
    }
}
