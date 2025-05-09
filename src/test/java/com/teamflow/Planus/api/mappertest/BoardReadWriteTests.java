package com.teamflow.Planus.api.mappertest;

import com.teamflow.Planus.domain.user.api.mapper.BoardWriteMapper;
import com.teamflow.Planus.vo.UserVO;
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

@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MapperScan(basePackages = "com.teamflow.Planus.domain.user.api.mapper")
public class BoardReadWriteTests {

    @Autowired
    private BoardWriteMapper boardWriteMapper;

    @Test
    @Transactional
    @DisplayName("글 작성 테스트")
    public void insertBoard() {
        String title = "글 작성 테스트";
        String content = "글 작성 테스트";
        Long boardId = 1L;
        String userId = "7594883d-c082-4170-ac22-b9016ef03c73";
        LocalDateTime now = LocalDateTime.now();
        Long groupId = 177687393623609344L;
        int result =
        boardWriteMapper.write(title,content,boardId,userId,now,groupId);

        log.info("BoardWriteMapper.write returns {}", result);
    }

    @Test
    @Transactional
    @DisplayName("유저 메일 리스트 조회")
    public void getUserEmailList(){
        List<UserVO> userMailList =
        boardWriteMapper.getuserEmailList();
        log.info("getUserEmailList returns {}", userMailList);
    }
}
