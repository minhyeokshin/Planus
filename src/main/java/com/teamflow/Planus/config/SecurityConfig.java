package com.teamflow.Planus.config;

import com.teamflow.Planus.domain.auth.login.security.CustomAuthenticationProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
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
                );

        return http.build();
    }

    // ✅ 로그인 성공 시 ROLE에 따라 리다이렉트 처리
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication
            ) throws IOException {

                for (GrantedAuthority auth : authentication.getAuthorities()) {

                    response.sendRedirect("/user/pages/index");
                    return;
                }

                // 예외적으로 아무 권한도 없을 때
                response.sendRedirect("/login?error=no_role");
            }
        };
    }


}
