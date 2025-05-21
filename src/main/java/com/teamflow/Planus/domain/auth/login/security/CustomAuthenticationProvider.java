package com.teamflow.Planus.domain.auth.login.security;

import com.teamflow.Planus.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Lazy
    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString().trim();

        // 1) Load user details
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        // 2) Retrieve stored bcrypt hash (no SHA-256)
        String storedHash = userDetails.getPassword().trim();  // ex. "$2a$10$LsgN1pN0L9V6UtYbrzQmdOdKkqDBxF6MTo2gUGcGfwj1Ruthi8m3O"

        // 3) Compare raw password with stored bcrypt hash
        if (!passwordEncoder.matches(rawPassword, storedHash)) {
            log.info("bcrypt matching failed for user: {}", username);
            throw new BadCredentialsException("Invalid credentials");
        }

        // 4) Authentication success
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, rawPassword, userDetails.getAuthorities());
        
        // 5) Generate JWT token
        String token = jwtTokenProvider.generateToken(auth);
        log.info("Generated JWT token for user {}: {}", username, token);
        
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Only support UsernamePasswordAuthenticationToken
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}