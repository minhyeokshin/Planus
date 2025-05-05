package com.teamflow.Planus.domain.auth.login.security;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Log4j2
public class CustomUserDetails implements UserDetails, Serializable {

    private String id;
    private String password;
    private String role;
    private String clientId;    // ✅ 추가 (User일 경우)
    private String adminNumber; // ✅ 추가 (Admin일 경우)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null || role.isBlank()) {
            log.info("role : {}", role);
            throw new IllegalStateException("✅ ROLE 값이 비어 있음 (null 또는 공백)");
        }
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
    }

    // 계정 상태 관련 설정 (true로 고정)
    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }

}
