package com.example.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // HTTP Basic 은 기본 인증 방식으로, 인증 프로세스가 실패할 때 특정 논리 구현 또는 클라이언트로 반환되는 응답 값 설정 등을 위해
        // 명시적으로 설정할 수 있다.
        // HttpSecurity 인스턴스의 httpBasic() 메서드를 호출해 Customizer 형식의 매개변수를 이용하면
        // 인증 방식과 관련한 일부 구성을 설정할 수 있다.
        http.httpBasic(c -> {
            c.realmName("OTHER");  // 영역(realm) 이름 변경
            c.authenticationEntryPoint(new CustomEntryPoint());
        });
        // cURL 호출시 -v 플래그 지정하면 영역 이름 변경되었는지 확인 가능
        // 그러나 HTTP Status Code 가 401 Unauthorized 일때만 WWW-Authenticate: Basic realm="OTHER" 확인 가능
        http.authorizeHttpRequests().anyRequest().authenticated();
        return http.build();
    }
}
