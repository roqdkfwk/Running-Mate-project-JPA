package com.suseok.run.jwtutill;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.suseok.run.model.dao.JwtDao;
import com.suseok.run.model.dto.JwtToken;
import com.suseok.run.model.dto.User;
import com.suseok.run.model.service.UserService;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private String jwtKey = "final-project-of-sooa-and-seokmin";

	private int accessTokenExpireTime = 3600;

	public int getAccessTokenExpireTime() {
		return accessTokenExpireTime;
	}

	public void setAccessTokenExpireTime(int accessTokenExpireTime) {
		this.accessTokenExpireTime = accessTokenExpireTime;
	}


	@Autowired
	JwtDao jd;
	@Autowired
	UserService us;

	public JwtUtil() {
	}

	public JwtUtil(String jwtKey, int accessTokenExpireTime,  JwtDao jd) {
		this.jwtKey = jwtKey;
		this.accessTokenExpireTime = accessTokenExpireTime;
		this.jd = jd;
	}

	// Access Token 생성 메서드
	public String createAccessToken(String userId) {
		long currentTime = System.currentTimeMillis(); // 현재 시간

		JwtBuilder jwtAccessTokenBuilder = Jwts.builder().claim("userId", userId).setIssuedAt(new Date(currentTime))
				.setExpiration(new Date(currentTime + accessTokenExpireTime * 1000))
				.signWith(SignatureAlgorithm.HS256, jwtKey.getBytes(StandardCharsets.UTF_8));

		return jwtAccessTokenBuilder.compact();
	}



	public boolean validate(String token) {
		try {
			Jwts.parser().setSigningKey(jwtKey.getBytes("UTF-8")).parseClaimsJws(token);
			System.out.println(token);
		} catch (Exception e) { // token을 파싱하는데 에러가 발생했다면 유효한 토큰이 아님.
			System.out.println(e);
			return false;
		}
		System.out.println("valid");
		return true;

	}

}
