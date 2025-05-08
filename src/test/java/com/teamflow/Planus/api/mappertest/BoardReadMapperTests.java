package com.teamflow.Planus.api.mappertest;

import com.teamflow.Planus.domain.user.api.mapper.BoardReadMapper;
import com.teamflow.Planus.vo.BoardViewLogVO;
import com.teamflow.Planus.vo.CommentVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MapperScan(basePackages = "com.teamflow.Planus.domain.user.api.mapper")
public class BoardReadMapperTests {

    @Autowired
    private BoardReadMapper boardReadMapper;

    @Test
    @Transactional
    @DisplayName("게시글 조회 테스트")
    public void findbyId() {
        int writeId = 1;
        boardReadMapper.findById(writeId);
        log.info("BoardReadMapper.findById returns {}", boardReadMapper.findById(writeId));
    }

    @Test
    @Transactional
    @DisplayName("댓글 조회 테스트")
    public void getComment(){
        List<CommentVO> commentVOList = boardReadMapper.getComment();

        log.info("BoardReadMapper.getComment returns {}", commentVOList);
        log.info("BoardReadMapper.getComment returns {}", commentVOList.size());
    }

    @Test
    @Transactional
    @DisplayName("댓글 작성 테스트")
    public void writeComment(){
        String content = "댓글 테스트";
        Long boardId = 1L;
        String userId = "3593d647-da08-4626-a96f-53a2771fc360";
        LocalDateTime now = LocalDateTime.now();
        Long groupId = 177687393623609344L;
        int result = boardReadMapper.writeComment(content,boardId,userId,now,groupId);
        log.info("BoardReadMapper.writeComment returns {}", result);
    }

    @Test
    @Transactional
    @DisplayName("게시글 삭제 테스트")
    public void deleteBoard(){

        int writeId = 2;
        LocalDateTime now = LocalDateTime.now();
        String userId = "596fd21f-b6df-42f0-af8b-b69ce32ea7f3";

        int result =
        boardReadMapper.deleteBoard(writeId,now ,userId);

        log.info("BoardReadMapper.deleteBoard returns {}", result);

    }

    @Test
    @Transactional
    @DisplayName("댓글 삭제 테스트")
    public void deleteComment(){
       
        int commentId = 4;
        LocalDateTime now = LocalDateTime.now();
        String userId = "596fd21f-b6df-42f0-af8b-b69ce32ea7f3";
        int result =
        boardReadMapper.deleteComment(commentId,now ,userId);

        log.info("BoardReadMapper.deleteComment returns {}", result);
    }

    @Test
    @Transactional
    @DisplayName("게시글 뷰 로그 테스트")
    public void insertViewLog(){
        String viewId = UUID.randomUUID().toString();
        int writeId = 2;
        String userId = "596fd21f-b6df-42f0-af8b-b69ce32ea7f3";
        LocalDateTime now =  LocalDateTime.now();
        boardReadMapper.viewLog(viewId,writeId,userId,now);
        log.info("BoardReadMapper.viewLog returns {}", viewId);
    }

    @Test
    @Transactional
    @DisplayName("게시글 뷰 조회 테스트")
    public void getViewLog(){
        List<BoardViewLogVO> viewLog =boardReadMapper.getViewLog();
        log.info("BoardReadMapper.getViewLog returns {}", viewLog);
    }













}
