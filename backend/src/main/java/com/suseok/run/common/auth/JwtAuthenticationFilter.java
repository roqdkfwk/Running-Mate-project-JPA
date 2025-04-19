package com.suseok.run.common.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. HTTP 요청의 헤더들 중에서 "Authorization"이라는 이름을 가진 헤더를 찾음
        String header = request.getHeader(JwtUtil.HEADER_STRING);

        logger.info("들어온 요청 URI: " + request.getRequestURI());
        logger.info("Authorization 헤더: " + request.getHeader("Authorization"));

        // 2. 헤더가 없거나 TOKEN_PREFIX로 시작하지 않으면 다음 필터로
        if (header == null || !header.startsWith(JwtUtil.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 토큰의 유효성 검사
        // TOKEN_PREFIX 제거
        String token = header.replace(JwtUtil.TOKEN_PREFIX, "");

        if (!jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4. principal에 PK만 저장
        Authentication authentication = jwtUtil.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

        // 4. principal에 객체를 저장
//        Long userSeq = jwtUtil.extractId(token);
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername(String.valueOf(userSeq));
//        UsernamePasswordAuthenticationToken auth
//                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
