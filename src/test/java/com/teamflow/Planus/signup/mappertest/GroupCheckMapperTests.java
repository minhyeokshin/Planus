package com.teamflow.Planus.signup.mappertest;

import com.teamflow.Planus.domain.auth.signup.mapper.GroupCheckMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MapperScan(basePackages = "com.teamflow.Planus.domain.auth.signup.mapper")
public class GroupCheckMapperTests {

    @Autowired
    private GroupCheckMapper groupCheckMapper;

    @Test
    @DisplayName("그룹 이름 존재 여부 확인 테스트 - 존재하는 경우")
    @Transactional
    public void testExistsByGroupName_WhenExists() {
        // given
        String groupName = "테스트";

        // when
        boolean exists = groupCheckMapper.existsByGroupName(groupName);

        // then
        assertTrue(exists);
        log.info("그룹 이름 '{}' 존재 여부: {}", groupName, exists);
    }

    @Test
    @DisplayName("그룹 이름 존재 여부 확인 테스트 - 존재하지 않는 경우")
    @Transactional
    public void testExistsByGroupName_WhenNotExists() {
        // given
        String groupName = "non-existent-group";

        // when
        boolean exists = groupCheckMapper.existsByGroupName(groupName);

        // then
        assertFalse(exists);
        log.info("그룹 이름 '{}' 존재 여부: {}", groupName, exists);
    }
}
