package com.suseok.run.model.service;

import java.util.Map;

import com.suseok.run.model.dto.User;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

	Map<String, Object> login(User user, HttpServletResponse response);


}
