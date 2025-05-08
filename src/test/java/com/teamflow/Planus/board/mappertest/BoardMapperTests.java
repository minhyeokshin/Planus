package com.teamflow.Planus.board.mappertest;

import com.teamflow.Planus.domain.user.board.mapper.BoardMapper;
import com.teamflow.Planus.vo.BoardVO;
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
@MapperScan(basePackages = "com.teamflow.Planus.domain.user.board.mapper")
public class BoardMapperTests {

    @Autowired
    private BoardMapper boardMapper;

    @Test
    @DisplayName("게시글 가져오기 테스트")
    public void getBoard(){
        List<BoardVO> boardVOList = boardMapper.getBoardList();
        log.info("boardVOList: {}", boardVOList);
        log.info("boardVOList size: {}", boardVOList.size());
    }
}
