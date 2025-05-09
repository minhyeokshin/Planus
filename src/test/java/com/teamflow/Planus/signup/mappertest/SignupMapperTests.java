package com.teamflow.Planus.signup.mappertest;

import com.teamflow.Planus.domain.auth.signup.mapper.SignupMapper;
import com.teamflow.Planus.vo.GroupVO;
import com.teamflow.Planus.vo.UserVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MapperScan(basePackages = "com.teamflow.Planus.domain.auth.signup.mapper")
public class SignupMapperTests {

    @Autowired
    private SignupMapper signupMapper;

    @Test
    @DisplayName("사용자 ID 중복 체크 테스트")
    @Transactional
    public void testExistCheckByUserId() {
        // given
        String loginId = "testuser";

        // when
        int count = signupMapper.existCheckByUserId(loginId);

        // then
        assertTrue(count >= 0);
        log.info("사용자 ID '{}' 중복 체크 결과: {}", loginId, count);
    }

    @Test
    @DisplayName("사용자 등록 테스트")
    @Transactional
    public void testInsertUser() {
        // given
        UserVO userVO = UserVO.builder()
                .userId(UUID.randomUUID().toString())
                .username("테스트 사용자")
                .groupId(177687393623609344L)
                .email("test@example.com")
                .phone("010-1234-5678")
                .role("USER")
                .loginId("testuser")
                .password("password123")
                .build();

        // when
        int result = signupMapper.insertUser(userVO);

        // then
        assertTrue(result > 0);
        log.info("사용자 등록 결과: {}", result);
    }

    @Test
    @DisplayName("그룹 등록 테스트")
    @Transactional
    public void testInsertGroup() {
        // given
        GroupVO groupVO = GroupVO.builder()
                .groupId(177687393623219344L)
                .groupName("테스트 그룹")
                .groupEmail("group@example.com")
                .gitHubRepo("test-repo")
                .gitHubOwner("test-owner")
                .gitHubToken("test-token")
                .gitHubTokenDate(LocalDateTime.now())
                .build();

        // when
        int result = signupMapper.insertGroup(groupVO);

        // then
        assertTrue(result > 0);
        log.info("그룹 등록 결과: {}", result);
    }
}
