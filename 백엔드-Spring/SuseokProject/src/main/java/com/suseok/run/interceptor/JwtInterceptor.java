package com.suseok.run.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.suseok.run.jwtutill.AuthRequired;
import com.suseok.run.jwtutill.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	
	// JWT 토큰이 포함된 요청 헤더의 이름을 정의
	private final String HEADER_AUTH = "access-token";

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	// 컨트롤러 메소드가 실행되기 전에 호출되는 메소드
	// JWT 토큰의 유형성을 검사한다.
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (!(handler instanceof HandlerMethod)) 
			return true;

		HandlerMethod method = (HandlerMethod) handler;

		// 메소드에 AuthRequired 어노테이션이 적용되었는지 확인.
		if (method.getMethodAnnotation(AuthRequired.class) != null) {

			// request 객체에서 요청 헤더를 꺼내서 확인.
			String accessToken = request.getHeader("Authorization");

			if (accessToken != null) {
				if (jwtUtil.validate(accessToken)) { // 유효한 토큰이라면
					return true; // 요청을 계속진행.
				}
			}
			
			response.setStatus(HttpStatus.UNAUTHORIZED.value());	// 유효하지 않은 토큰이거나 토큰이 없는 경우 401 상태 코드 설정
			return false; // 요청을 중지.
		}
		
		return true;
	}

}

// 클래스 설명
// Spring Boot 어플리케이션에서 HTTP 요청을 가로채고, 특정 엔드포인트에 대한 JWT 토큰의 유효성을 검사한다.
// `HandlerInterceptor` 인터페이스를 구현하여, 요청이 컨트롤러 메소드에 도달하기 전에 인증 로직을 실행한다.
