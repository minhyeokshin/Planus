package com.teamflow.Planus.domain.user.board.mapper;

import com.teamflow.Planus.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardVO> getBoardList();
}
