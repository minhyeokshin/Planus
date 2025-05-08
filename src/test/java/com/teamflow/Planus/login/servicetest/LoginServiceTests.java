package com.teamflow.Planus.login.servicetest;

import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.auth.login.service.LoginService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
@SpringBootTest
public class LoginServiceTests {

    @Autowired
    private LoginService loginService;

    @BeforeEach
    @Transactional
    void setup() {
        // 테스트용 가짜 UserDetails 객체 생성
        CustomUserDetails testUser = new CustomUserDetails();
        testUser.setRole("USER");
        testUser.setUserId("1e31be5f-09d8-4490-aea8-d29198cfa64c");
        testUser.setPassword("password");
        testUser.setName("Test User");
        testUser.setGroupId(177687393623609344L);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트 - 성공 케이스")
    @Transactional
    public void testPasswordChangeSuccess() {
        // given
        String password = "oldPassword123!";
        String newPassword = "newPassword123!";
        String newPasswordCheck = "newPassword123!";

        // when
        int result = loginService.passwordChange(password, newPassword, newPasswordCheck);

        // then
        assertTrue(result > 0);
        log.info("비밀번호 변경 결과: {}", result);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트 - 실패 케이스 (비밀번호 불일치)")
    @Transactional
    public void testPasswordChangeFailure() {
        // given
        String password = "oldPassword123!";
        String newPassword = "newPassword123!";
        String newPasswordCheck = "differentPassword123!";

        // when
        int result = loginService.passwordChange(password, newPassword, newPasswordCheck);

        // then
        assertEquals(0, result);
        log.info("비밀번호 변경 실패 결과: {}", result);
    }
}
