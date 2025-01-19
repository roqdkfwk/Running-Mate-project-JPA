package com.suseok.run.model.service;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.suseok.run.jwtutill.JwtUtil;
import com.suseok.run.model.dao.JwtDao;
import com.suseok.run.model.dto.User;

import jakarta.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final JwtUtil jwtUtil;
	private final JwtDao jwtDao;
	private final UserService userService;

	public Map<String, Object> login(User user, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();

		User dbUser = userService.loginUser(user);

		if (dbUser == null) {
			result.put("message","존재하지 않는 유저입니다");
			return result; 
		}

		String accessToken = jwtUtil.createAccessToken(dbUser.getUserId());

		result.put("accessToken", accessToken);
		result.put("userId", dbUser.getUserId());

		return result; // 유저가 존재하는 경우 결과 반환
	}
}
