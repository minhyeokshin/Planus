package com.teamflow.Planus.api.servicetest;

import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.api.mapper.BoardWriteMapper;
import com.teamflow.Planus.domain.user.api.service.BoardReadService;
import com.teamflow.Planus.domain.user.board.mapper.BoardMapper;
import com.teamflow.Planus.dto.BoardViewLogDTO;
import com.teamflow.Planus.dto.CommentDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Log4j2
@SpringBootTest
public class BoardReadServiceTests {

    @Autowired
    private BoardReadService boardReadService;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private BoardWriteMapper boardWriteMapper;


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
    @DisplayName("댓글 조회 테스트")
    public void getComment(){
        int writeId = 3;

        List<CommentDTO> commentDTOList = boardReadService.getComment(writeId);

        log.info("boardReadService.getComment returns {}", commentDTOList);
        log.info("boardReadService.getComment returns {}", commentDTOList.size());
    }

    @Test
    @Transactional
    @DisplayName("댓글 작성 테스트")
    public void writeComment(){

        String content = "댓글 테스트";
        Long boardId = 3L;

        int result = boardReadService.writeComment(content,boardId);
        log.info("boardReadService.writeComment returns {}", result);
    }

    @Test
    @Transactional
    @DisplayName("게시글 삭제 테스트")
    public void deleteBoard(){

        int writeId = 2;

        int result =
                boardReadService.deleteBoard(writeId);

        log.info("boardReadService.deleteBoard returns {}", result);

    }

    @Test
    @Transactional
    @DisplayName("댓글 삭제 테스트")
    public void deleteComment(){

        int commentId = 4;

        int result =
                boardReadService.deleteComment(commentId);

        log.info("boardReadService.deleteComment returns {}", result);
    }

    @Test
    @Transactional
    @DisplayName("게시글 뷰 로그 테스트")
    public void insertViewLog(){
        String viewId = UUID.randomUUID().toString();
        int writeId = 2;
        String userId = "596fd21f-b6df-42f0-af8b-b69ce32ea7f3";
        LocalDateTime now =  LocalDateTime.now();
        boardReadService.viewLog(writeId);
        log.info("boardReadService.viewLog returns {}", viewId);
    }

    @Test
    @Transactional
    @DisplayName("게시글 뷰 조회 테스트")
    public void getViewLog(){
        int writeId = 2;
        List<BoardViewLogDTO> viewLog =boardReadService.getViewLog(writeId);
        log.info("boardReadService.getViewLog returns {}", viewLog);
    }













}
