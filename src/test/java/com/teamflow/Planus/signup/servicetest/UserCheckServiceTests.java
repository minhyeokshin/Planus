package com.teamflow.Planus.signup.servicetest;

import com.teamflow.Planus.domain.auth.signup.service.UserCheckService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@SpringBootTest
public class UserCheckServiceTests {

    @Autowired
    private UserCheckService userCheckService;

    @Test
    @DisplayName("사용자 ID 존재 여부 확인 테스트")
    @Transactional
    public void testExistsByUserId() {
        // given
        String userId = "testuser";

        // when
        boolean exists = userCheckService.existsByUserId(userId);

        // then
        log.info("사용자 ID '{}' 존재 여부: {}", userId, exists);
    }

    @Test
    @DisplayName("그룹 ID 존재 여부 확인 테스트")
    @Transactional
    public void testExistsByGroupId() {
        // given
        Long groupId = 177687393623219344L;

        // when
        boolean exists = userCheckService.excistsByGroupId(groupId);

        // then
        log.info("그룹 ID '{}' 존재 여부: {}", groupId, exists);
    }
}
