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
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()); // CSRF 비활성화
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/login", "/board/1/posts").permitAll() // 자유 게시판과 로그인 페이지는 모든 사용자 접근 허용
                .requestMatchers("/club/{clubId}/board/2/posts").hasRole("CLUB_PRESIDENT") // 공지 게시판은 동아리장만 접근 허용
                .requestMatchers("/board/3/club/{clubId}/posts").hasRole("CLUB_PRESIDENT") // 활동 게시판은 동아리장만 접근 허용
                .requestMatchers("/club/{clubId}/board/4/posts").hasAnyRole("CLUB_MEMBER", "CLUB_PRESIDENT") // 커뮤니티내 자유 게시판은 동아리 회원과 동아리장만 접근 허용
                .anyRequest().permitAll() // 기타 모든 요청은 허용
        );
        // 폼 로그인 설정
        http.formLogin((formLogin) -> formLogin.loginPage("/login") // 폼으로 로그인, 로그인 페이지 URL 적어주기 GET
                .usernameParameter("studentId")
                .passwordParameter("password")
                .defaultSuccessUrl("/") // 로그인 성공 시 이동할 URL
        );
        // 로그아웃 설정
        http.logout((logout) -> logout
                .logoutUrl("/logout") // 로그아웃 URL
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
        );
        return http.build();
    }
}

// FilterChain : 모든 유저의 요청과 서버의 응답 사이에 자동으로 실행해주고 싶은 코드 담는 곳
