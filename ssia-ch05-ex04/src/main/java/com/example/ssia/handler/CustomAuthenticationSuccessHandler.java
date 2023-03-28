package com.example.ssia.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    // 로그인 양식 인증 성공시 맞춤 구성을 위해 AuthenticationSuccessHandler 를 구현한다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // onAuthenticationSuccess() 메서드는 서블릿요청, 응답, Authentication 을 받아 로그인한 사용자에게 부여된 권한에 따라 리디렉션을 수행할 수 있다.
        var authorities = authentication.getAuthorities();

        var auth = authorities.stream()
                .filter(a -> a.getAuthority().equals("read"))
                .findFirst(); // read 권한이 없으면 비어 있는 Optional 객체 반환

        if (auth.isPresent()) {
            response.sendRedirect("/home");
        } else {
            response.sendRedirect("/error");
        }
    }
}
