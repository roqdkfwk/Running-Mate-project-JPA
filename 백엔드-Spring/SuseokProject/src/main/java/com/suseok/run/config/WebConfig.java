package com.suseok.run.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.suseok.run.interceptor.JwtInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	// 인터셉터 등 처리 가능
	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)	// jwtInterceptor를 등록
				.addPathPatterns("/**")	// 모든 경로에 대해 interceptor를 적용
				.excludePathPatterns("/login/**", "/signup", "/search/**");	// 로그인, 회원가입, 검색 경로에 대해서는 interceptor 제외
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")	// 모든 경로에 대해 CORS를 적용
				.allowedOrigins("*")	// 모든 도메인에서의 접근을 허용
				.allowedMethods("GET", "POST", "PUT", "DELETE");	// 모든 메소드를 허용
	}
}
