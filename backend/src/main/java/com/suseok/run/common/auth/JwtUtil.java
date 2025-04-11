package com.suseok.run.common.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ISSUER = "ssafy.com";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * 토큰 생성 메서드
     */
    public String createToken(
            Map<String, Object> claims,
            String subject,
            Long expiration,
            boolean isRefreshToken
    ) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", isRefreshToken ? "refresh" : "JWT");

        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuer(ISSUER)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * AccessToken 발급 메서드
     */
    public String generateAccessToken(com.suseok.run.model.entity.User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("name", user.getUserName());

        return createToken(claims, user.getUserSeq().toString(), expiration, false);
    }

    /**
     * RefreshToken 발급 메서드
     * 유효기간은 1h * 24 * 15 = 15days
     */
    public String generateRefreshToken(Long userSeq) {
        return createToken(new HashMap<>(), userSeq.toString(), expiration * 24 * 15, true);
    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            // 접두사 제거
            String actualToken = token.replace(TOKEN_PREFIX, "");
            Jwts.parserBuilder().setSigningKey(secretKey)
                    .requireIssuer(ISSUER)
                    .build()
                    .parseClaimsJws(actualToken)
                    .getBody();

            return !isTokenExpired(actualToken);
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰에서 사용자 ID를 추출
    public Long extractId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 인증 정보를 담은 Authentication 객체 반환
    public Authentication getAuthentication(String token) {

        Claims claims = extractAllClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        return new UsernamePasswordAuthenticationToken(
                new User(claims.getSubject(), "", authorities),
                token,
                authorities
        );
    }
}
