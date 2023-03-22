package com.example.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        /*
        UserDetailService 를 재정의하게 되면
        자동 구성된 기본 구성 요소 대신 컨텍스트에 추가한 userDetailsService 인스턴스를 이용해야 한다.
        1. 자격 증명(사용자 이름 및 암호)이 있는 사용자를 하나 이상 만든다.
        2. 사용자를 UserDetailService 에서 관리하도록 추가한다.
        3. 주어진 암호를 저장하고 검증하는 PasswordEncoder 형식의 Bean 을 정의한다.
        */
        var userDetailsService = new InMemoryUserDetailsManager();

        // 1. User 빌더 클래스로 UserDetails 생성
        // 사용자 이름, 암호, 권한
        var user = User.withUsername("john")
                .password("12345")
                .authorities("read")
                .build();
        // 2. UserDetailsService 에서 관리하도록 사용자 추가
        userDetailsService.createUser(user);

        return userDetailsService;
    }

    // 3. PasswordEncoder 빈을 컨텍스트에 추가
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
