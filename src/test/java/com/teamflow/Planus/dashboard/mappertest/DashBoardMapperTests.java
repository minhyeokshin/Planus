package com.teamflow.Planus.dashboard.mappertest;

import com.teamflow.Planus.domain.user.dashboard.Mapper.DashBoardMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MapperScan(basePackages = "com.teamflow.Planus.domain.user.dashboard.mapper")
public class DashBoardMapperTests {

    @Autowired
    private DashBoardMapper dashBoardMapper;

    @Test
    @DisplayName("팀원 이름 가져오기 ")
    public void getUserName(){
        List<String> userName = dashBoardMapper.getUserNameList(177687393623609344L);
        log.info("userName: {}", userName);
    }
}
