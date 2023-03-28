package com.example.ssia.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomEntryPoint implements AuthenticationEntryPoint {
    // 인증 실패 시 응답 맞춤 구성을 위해서는 AuthenticationEntryPoint 를 구현한다.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("message", "Luke, I am your father!");
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }
}
