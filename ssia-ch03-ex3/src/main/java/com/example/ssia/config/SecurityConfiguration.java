package com.example.ssia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultLdapUsernameToDnMapper;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.security.ldap.userdetails.PersonContextMapper;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                .anyRequest().authenticated()) // 모든 요청에 인증이 필요함
                .httpBasic(withDefaults());
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("ou=groups", "uid")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:33389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .passwordAttribute("userPassword");
    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        var cs = new DefaultSpringSecurityContextSource("ldap://127.0.0.1:33389/dc=springframework,dc=org");
//        cs.afterPropertiesSet();
//
//        var manager = new LdapUserDetailsManager(cs);
//        manager.setUsernameMapper(new DefaultLdapUsernameToDnMapper("ou=groups", "uid"));
//        manager.setGroupSearchBase("ou=groups");
//
//        return manager;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
}
