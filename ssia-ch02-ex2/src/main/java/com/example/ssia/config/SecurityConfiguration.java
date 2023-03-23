package com.example.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration { // extends WebSecurityConfigurerAdapter -> deprecated
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

    /*
    WebSecurityConfigurerAdapter 클래스가 Spring Security 5.7.0 버전 이후로 deprecated 되면서
    해당 클래스를 상속하여 config() 메서드를 오버라이딩하여 설정을 초기화 하는 것이 아니라,
    사용자가 구성 요소 기반 보안 구성을 하길 권장한다.
    직접 SecurityFilterChain / WebSecurityCustomizer 빈을 등록해 각각 HttpSecurity / WebSecurity 를 구성해야 한다.
    */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authz -> authz
    //            .anyRequest().authenticated()) // 모든 요청에 인증이 필요함
                .anyRequest().permitAll()) // 인증 없이 요청 가능
            .httpBasic(withDefaults());
        return http.build();
    }


    /*
    위처럼 UserDetailsService, PasswordEncoder 빈을 직접 정의하지 않고 AuthenticationManager / SecurityFilterChain 빈을 반환하는 메서드로 코드를 줄일 수 있다.
     */
//    @Bean
//    public AuthenticationManager configure(AuthenticationManagerBuilder auth) throws Exception {
//            var userDetailsService = new InMemoryUserDetailsManager();
//            var user = User.withUsername("john")
//                    .password("12345")
//                    .authorities("read")
//                    .build();
//                userDetailsService.createUser(user);
//
//        return auth.userDetailsService(userDetailsService)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .and().build();
//    }
}
