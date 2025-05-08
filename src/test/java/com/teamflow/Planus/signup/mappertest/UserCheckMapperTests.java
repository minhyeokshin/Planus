package com.teamflow.Planus.signup.mappertest;

import com.teamflow.Planus.domain.auth.signup.mapper.UserCheckMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MapperScan(basePackages = "com.teamflow.Planus.domain.auth.signup.mapper")
public class UserCheckMapperTests {

    @Autowired
    private UserCheckMapper userCheckMapper;

    @Test
    @DisplayName("사용자 ID 존재 여부 확인 테스트")
    @Transactional
    public void testExistsByUserId() {
        // given
        String userId = "testuser";

        // when
        boolean exists = userCheckMapper.existsByUserId(userId);

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
        boolean exists = userCheckMapper.existsByGroupId(groupId);

        // then
        log.info("그룹 ID '{}' 존재 여부: {}", groupId, exists);
    }
}
