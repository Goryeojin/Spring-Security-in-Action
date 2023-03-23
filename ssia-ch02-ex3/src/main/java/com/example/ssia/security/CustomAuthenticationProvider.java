package com.example.ssia.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    /*
    AuthenticationProvider 는 AuthenticationManager 에서 요청을 받은 후
    사용자를 찾는 작업을 UserDetailsService 에, 암호를 검증하는 작업을 PasswordEncoder 에 위임한다.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 인증 전체 논리 정의

        String username = authentication.getName(); // Principal 인터페이스의 getName() 메서드를 Authentication 에서 상속받아 사용.
        String password = String.valueOf(authentication.getCredentials());

        if ("john".equals(username) && "12345".equals(password)) { // UserDetailsService 와 PasswordEncoder 호출해 테스트
            return  new UsernamePasswordAuthenticationToken(username, password, List.of());
        } else {
            throw new AuthenticationCredentialsNotFoundException("Error in authentication!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Authentication 형식의 구현 정의
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
