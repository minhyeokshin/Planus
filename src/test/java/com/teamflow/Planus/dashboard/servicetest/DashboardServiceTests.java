package com.teamflow.Planus.dashboard.servicetest;


import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.dashboard.Service.DashBoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@SpringBootTest
public class DashboardServiceTests {

    @Autowired
    private DashBoardService dashBoardService;

    @BeforeEach
    @Transactional
    void setup() {
        // 테스트용 가짜 UserDetails 객체 생성
        CustomUserDetails testUser = new CustomUserDetails();
        testUser.setRole("USER");
        testUser.setUserId("596fd21f-b6df-42f0-af8b-b69ce32ea7f3");
        testUser.setPassword("password");
        testUser.setName("Test User");
        testUser.setGroupId(177687393623609344L);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Test
    @DisplayName("팀원 이름 가져오기")
    public void getUserName(){
        List<String> userNameList = dashBoardService.getUserNameList();
        log.info("userNameList: {}", userNameList);

    }


}
