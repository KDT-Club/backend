package com.ac.su;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig { //여기서 Spring Security 설정함

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //어떤 페이지를 검사할지 설정 가능
        http.csrf((csrf) -> csrf.disable()); //CSRF 보안 기능 끔
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll() ////모든 페이지 접속 허용
        );
        return http.build();
    }
}


// FilterChain : 모든 유저의 요청과 서버의 응답 사이에 자동으로 실행해주고 싶은 코드 담는 곳