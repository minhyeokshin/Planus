package com.teamflow.Planus.domain.user.api.service;

import com.teamflow.Planus.dto.BoardDTO;
import com.teamflow.Planus.dto.BoardViewLogDTO;
import com.teamflow.Planus.dto.CommentDTO;

import java.util.List;

public interface BoardReadService {
    BoardDTO findById(int writeId);
    List<CommentDTO> getComment(int writeId);
    int writeComment(String content, Long boardId);
    int deleteBoard(int writeId);
    int deleteComment(int commentId);
    int viewLog(int writeId);
    List<BoardViewLogDTO> getViewLog(int writeId);
}
