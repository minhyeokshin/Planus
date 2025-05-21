package com.teamflow.Planus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamflow.Planus.domain.auth.login.security.CustomAuthenticationProvider;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    // 🔐 보안 필터 체인 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationProvider customAuthProvider) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/api/**")))
                .authenticationProvider(customAuthProvider)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login", "/signup", "/groupsignup", "/static/**", "/error").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN", "VIEW")
//                        .requestMatchers("/users/**").hasRole("VIEW")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .successHandler(customSuccessHandler())
                    .failureUrl("/login?error=true")
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ 로그인 성공 시 JSON 응답으로 JWT 토큰을 전달
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            String token = jwtTokenProvider.generateToken(authentication);
            
            // JWT 토큰을 httpOnly 쿠키로 설정
            Cookie jwtCookie = new Cookie("jwtToken", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true); // HTTPS에서만 전송
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(3600); // 1시간
            response.addCookie(jwtCookie);

            // 리다이렉트 URL 결정
            String redirectUrl = determineRedirectUrl(authentication);
            response.sendRedirect(redirectUrl);
        };
    }

    private String determineRedirectUrl(Authentication authentication) {
        return "/user/pages/index";
    }

}
