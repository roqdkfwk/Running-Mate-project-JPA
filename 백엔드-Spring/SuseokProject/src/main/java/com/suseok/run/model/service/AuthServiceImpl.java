package com.suseok.run.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.suseok.run.jwtutill.JwtUtil;
import com.suseok.run.model.dao.JwtDao;
import com.suseok.run.model.dto.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthServiceImpl implements AuthService {


	private final JwtUtil jwtUtil;
	private final JwtDao jd;
	private final UserService us;

	private int refreshTokenExpireTime;

	public AuthServiceImpl(JwtUtil jwtUtil, UserService us,JwtDao jd) {
		this.jwtUtil = jwtUtil;
		this.us = us;
		this.jd=jd;
		refreshTokenExpireTime = jwtUtil.getRefreshTokenExpireTime();
	}

	public Map<String, Object> login(User user, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();

		User dbUser = us.loginUser(user);

		if (dbUser == null) {
			result.put("message","존재하지 않는 유저입니다");
			return result; 
		}

		String accessToken = jwtUtil.createAccessToken(dbUser.getUserId());
		String refreshToken = jwtUtil.createRefreshToken(dbUser.getUserId());

		Cookie cookie = new Cookie("refreshToken", refreshToken);
		cookie.setMaxAge(refreshTokenExpireTime);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);

		result.put("accessToken", accessToken);
		result.put("userId", dbUser.getUserId());

		return result; // 유저가 존재하는 경우 결과 반환
	}

	public void invalidateToken(String userId, HttpServletResponse response) {
		// 쿠키에서 refreshToken 삭제
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setMaxAge(0);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);

		// 서버 측에서 refreshToken을 무효화하는 작업
		jd.deleteRefreshToken(userId);
	}

}
