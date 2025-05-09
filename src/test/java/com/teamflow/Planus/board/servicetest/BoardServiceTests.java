package com.teamflow.Planus.board.servicetest;

import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.board.service.BoardService;
import com.teamflow.Planus.dto.BoardDTO;
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
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

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
    @DisplayName("게시판 가져오기 테스트")
    public void getBoard(){
        String boardId = "1";
        List<BoardDTO> boardDTOList = boardService.getBoardList(boardId);
        log.info("boardDTOList: {}", boardDTOList);
        log.info("boardDTOList size: {}", boardDTOList.size());

    }
}
