package com.suseok.run.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.suseok.run.jwtutill.JwtUtil;
import com.suseok.run.model.dao.JwtDao;
import com.suseok.run.model.dto.User;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthServiceImpl implements AuthService {

	private final JwtUtil jwtUtil;
	private final JwtDao jd;
	private final UserService us;

	public AuthServiceImpl(JwtUtil jwtUtil, UserService us,JwtDao jd) {
		this.jwtUtil = jwtUtil;
		this.us = us;
		this.jd=jd;
	}
	
	@Override
	public Map<String, Object> login(User user, HttpServletResponse response) {
		
		Map<String, Object> result = new HashMap<>();
		
		User dbUser = us.loginUser(user);
		
		if (dbUser == null) {
			result.put("message", "존재하지 않는 유저입니다.");
			return result;
		}
		
		String accessToken = jwtUtil.createAccessToken(dbUser.getUserId());
		
		result.put("accessToken", accessToken);
		result.put("userId", dbUser.getUserId());
		
		return result;
	}
}
