package com.suseok.run.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suseok.run.jwtutill.AuthRequired;
import com.suseok.run.model.dto.User;
import com.suseok.run.model.service.AuthService;
import com.suseok.run.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping({ "", "/" })
@Tag(name = "AuthRestController", description = "Authentication")
public class AuthController {

	private final AuthService as;

	public AuthController(AuthService as) {
		this.as = as;
	}


	@GetMapping
	public ResponseEntity<?> home() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/strava-callback")
	public ResponseEntity<?> strava() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/login")
	@Operation(summary = "login")
	public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response)
			throws UnsupportedEncodingException {
		Map<String, Object> result = as.login(user, response);
		if (result.containsKey("message")) {
			return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
	
	@AuthRequired
	@DeleteMapping("/logout")
	@Operation(summary = "logout")
	public ResponseEntity<?> logout(@RequestHeader("userId") String userId, HttpServletResponse response) {
		//프론트에서 엑세스토큰 삭제 후 요청
		return new ResponseEntity<>(HttpStatus.OK);
	}



}
