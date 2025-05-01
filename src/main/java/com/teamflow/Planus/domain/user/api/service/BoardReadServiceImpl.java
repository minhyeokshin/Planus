package com.teamflow.Planus.domain.user.api.service;

import com.teamflow.Planus.domain.user.api.mapper.BoardReadMapper;
import com.teamflow.Planus.dto.BoardDTO;
import com.teamflow.Planus.dto.CommentDTO;
import com.teamflow.Planus.vo.BoardVO;
import com.teamflow.Planus.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardReadServiceImpl implements BoardReadService {

    private final BoardReadMapper boardReadMapper;

    @Override
    public BoardDTO findById(int writeId) {

        BoardVO boardVO = boardReadMapper.findById(writeId);
        BoardDTO boardDTO = BoardDTO.builder()
                .writeId(boardVO.getWriteId())
                .boardId(boardVO.getBoardId())
                .userId(boardVO.getUserId())
                .userName(boardVO.getUserName())
                .createdAt(boardVO.getCreatedAt())
                .title(boardVO.getTitle())
                .content(boardVO.getContent())
                .build();

        return boardDTO;
    }

    @Override
    public List<CommentDTO> getComment(int writeId) {
        List<CommentVO> commentVOList = boardReadMapper.getComment(writeId);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentVO commentVO : commentVOList) {
            CommentDTO commentDTO = CommentDTO.builder()
                    .commentId(commentVO.getCommentId())
                    .boardId(commentVO.getBoardId())
                    .createdAt(commentVO.getCreatedAt())
                    .userId(commentVO.getUserId())
                    .userName(commentVO.getUserName())
                    .content(commentVO.getContent())
                    .build();
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    @Override
    public int writeComment(String content, Long boardId) {
        String userId = "user-1";
        log.info("BoardReadServiceImpl");
        log.info("userId: {}", userId);
        log.info("content: {}", content);
        log.info("boardId: {}", boardId);
        return boardReadMapper.writeComment(content, boardId, userId);
    }

    @Override
    public int deleteBoard(int writeId) {
        return boardReadMapper.deleteBoard(writeId);
    }

    @Override
    public int deleteComment(int commentId) {
        return boardReadMapper.deleteComment(commentId);
    }
}
