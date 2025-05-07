package com.teamflow.Planus.domain.auth.login.service;

import com.teamflow.Planus.domain.auth.login.repository.LoginMapper;
import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public int passwordChange(String password, String newPassword, String newPasswordCheck) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        String userId = currentUser.getUserId();
        int result = 0;
        if (!newPassword.equals(newPasswordCheck)) {
            log.info("비번 변경 실행 안함");
            return 0;
        }else{
            String newPasswordEncoder = passwordEncoder.encode(newPassword);
            result = loginMapper.passwordChange(userId,newPasswordEncoder);
        }
        log.info("userId: {}", userId);
        log.info("newPassword: {}", newPassword);
        log.info("newPasswordCheck: {}", newPasswordCheck);
        log.info("passwordEncoder.encode(newPassword): {}", passwordEncoder.encode(newPassword));
        log.info("passwordChange result: {}", result);
        return result;
    }
}
