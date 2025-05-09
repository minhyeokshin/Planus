package com.teamflow.Planus.login.mappertest;

import com.teamflow.Planus.domain.auth.login.repository.LoginMapper;
import com.teamflow.Planus.vo.UserVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MapperScan(basePackages = "com.teamflow.Planus.domain.auth.login.repository")
public class LoginMapperTests {

    @Autowired
    private LoginMapper loginMapper;

    @Test
    @DisplayName("로그인 테스트")
    @Transactional
    public void testLogin() {
        // given
        String loginId = "39a311ccee";

        // when
        UserVO user = loginMapper.login(loginId);

        // then
        assertNotNull(user);
        assertEquals(loginId, user.getLoginId());
        log.info("로그인된 사용자 정보: {}", user);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
    @Transactional
    public void testPasswordChange() {
        // given
        String userId = "1e31be5f-09d8-4490-aea8-d29198cfa64c";
        String newPassword = "newPassword123!";

        // when
        int result = loginMapper.passwordChange(userId, newPassword);

        // then
        assertTrue(result > 0);
        log.info("비밀번호 변경 결과: {}", result);
    }
}
