package com.teamflow.Planus.api.servicetest;

import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.api.service.BoardWriteService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@SpringBootTest
public class BoardWriteServiceTests {

    @Autowired
    private BoardWriteService boardWriteService;

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
    @Transactional
    @DisplayName("글 작성 테스트")
    public void insertBoard() {
        String title = "글 작성 테스트";
        String content = "글 작성 테스트";
        Long boardId = 1L;
        int result =
                boardWriteService.write(title,content,boardId);

        log.info("BoardWriteMapper.write returns {}", result);
    }
}
