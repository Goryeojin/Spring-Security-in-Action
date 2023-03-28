package com.example.ssia.config;

import com.example.ssia.handler.CustomAuthenticationFailureHandler;
import com.example.ssia.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private CustomAuthenticationFailureHandler failureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 인증 방식을 사용자 인터페이스 친화적인 양식 기반 로그인으로 구성하는 방법.
        http.formLogin() // httpBasic() 대신 formLogin() 메서드를 호출한다.
        // 접근 시 로그인 페이지로 리디렉션된다.
        // 위 메서드는 FormLoginConfigurer<HttpSecurity> 형식의 객체를 반환한다.
//            .defaultSuccessUrl("/home", true)
            .successHandler(successHandler)
            .failureHandler(failureHandler)
            .and()
            .httpBasic();

        http.authorizeHttpRequests().anyRequest().authenticated();
        return http.build();
    }
}
