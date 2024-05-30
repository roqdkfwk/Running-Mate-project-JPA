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

	private String jwtKey = "sooa-seokmin-love-forever";

	private int accessTokenExpireTime = 3600;

	public int getAccessTokenExpireTime() {
		return accessTokenExpireTime;
	}

	public void setAccessTokenExpireTime(int accessTokenExpireTime) {
		this.accessTokenExpireTime = accessTokenExpireTime;
	}

	public int getRefreshTokenExpireTime() {
		return refreshTokenExpireTime;
	}

	public void setRefreshTokenExpireTime(int refreshTokenExpireTime) {
		this.refreshTokenExpireTime = refreshTokenExpireTime;
	}

	private int refreshTokenExpireTime = 2592000;

	@Autowired
	JwtDao jd;
	@Autowired
	UserService us;

	public JwtUtil() {
	}

	public JwtUtil(String jwtKey, int accessTokenExpireTime, int refreshTokenExpireTime, JwtDao jd) {
		this.jwtKey = jwtKey;
		this.accessTokenExpireTime = accessTokenExpireTime;
		this.refreshTokenExpireTime = refreshTokenExpireTime;
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

	// Refresh Token 생성 메서드
	public String createRefreshToken(String userId) {
		long currentTime = System.currentTimeMillis(); // 현재 시간

		JwtBuilder jwtRefreshTokenBuilder = Jwts.builder().claim("userId", userId).setIssuedAt(new Date(currentTime))
				.setExpiration(new Date(currentTime + refreshTokenExpireTime * 1000))
				.signWith(SignatureAlgorithm.HS256, jwtKey.getBytes(StandardCharsets.UTF_8));
		User user = us.selectById(userId);
		int userSeq = user.getUserSeq();

		JwtToken jwtToken = new JwtToken(userSeq, jwtRefreshTokenBuilder.compact());

		jd.insert(jwtToken);

		return jwtRefreshTokenBuilder.compact();
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
