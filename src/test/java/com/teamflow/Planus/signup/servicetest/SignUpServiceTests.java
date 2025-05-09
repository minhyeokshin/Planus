package com.teamflow.Planus.signup.servicetest;

import com.teamflow.Planus.domain.auth.signup.service.SignupService;
import com.teamflow.Planus.dto.GroupDTO;
import com.teamflow.Planus.dto.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
@SpringBootTest
public class SignUpServiceTests {

    @Autowired
    private SignupService signupService;

    @Test
    @DisplayName("사용자 ID 존재 여부 확인 테스트")
    @Transactional
    public void testIsUserIdExist() {
        // given
        String userId = "testuser";

        // when
        boolean exists = signupService.isUserIdExist(userId);

        // then
        log.info("사용자 ID '{}' 존재 여부: {}", userId, exists);
    }

    @Test
    @DisplayName("사용자 회원가입 테스트")
    @Transactional
    public void testSignUp() {
        // given
        UserDTO userDTO = UserDTO.builder()
                .username("테스트 사용자")
                .groupId(177687393623609344L)
                .email("test@example.com")
                .userPhone1("010")
                .userPhone2("1234")
                .userPhone3("5678")
                .role("USER")
                .loginId("testuser")
                .password("password123")
                .build();

        // when
        boolean result = signupService.signUp(userDTO);

        // then
        assertTrue(result);
        log.info("회원가입 결과: {}", result);
    }

    @Test
    @DisplayName("그룹 회원가입 테스트")
    @Transactional
    public void testGroupSignUp() {
        // given
        GroupDTO groupDTO = GroupDTO.builder()
                .groupName("테스트 그룹")
                .groupEmail("group@example.com")
                .gitHubRepo("test-repo")
                .gitHubOwner("test-owner")
                .gitHubToken("test-token")
                .gitHubTokenDate(LocalDateTime.now())
                .build();

        // when
        boolean result = signupService.groupSignUp(groupDTO);

        // then
        assertTrue(result);
        log.info("그룹 회원가입 결과: {}", result);
    }
}
