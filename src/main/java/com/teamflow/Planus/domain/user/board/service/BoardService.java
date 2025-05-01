package com.teamflow.Planus.domain.user.board.service;

import com.teamflow.Planus.dto.BoardDTO;

import java.util.List;

public interface BoardService {
    List<BoardDTO> getBoardList(int boardId);
}
