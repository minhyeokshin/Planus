package com.teamflow.Planus.domain.user.api.service;

import com.teamflow.Planus.cache.BoardCache;
import com.teamflow.Planus.cache.CommentCache;
import com.teamflow.Planus.cache.LoginCache;
import com.teamflow.Planus.domain.user.api.mapper.BoardReadMapper;
import com.teamflow.Planus.domain.user.board.mapper.BoardMapper;
import com.teamflow.Planus.dto.BoardDTO;
import com.teamflow.Planus.dto.CommentDTO;
import com.teamflow.Planus.vo.BoardVO;
import com.teamflow.Planus.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardReadServiceImpl implements BoardReadService {

    private final BoardReadMapper boardReadMapper;
    private final BoardMapper boardMapper;

    @Override
    public BoardDTO findById(int writeId) {
        List<BoardVO> boardVOList = BoardCache.getInstance().getBoardVOList();
        if (boardVOList == null){
            boardVOList = boardMapper.getBoardList();
            log.info("db 통신함");
            BoardCache.getInstance().setBoardVOList(boardVOList);
        }

        boardVOList =
                boardVOList.stream()
                        .filter(vo ->vo.getWriteId().equals(String.valueOf(writeId)))
                        .toList();

        BoardVO boardVO = boardVOList.get(0);

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
        List<CommentVO> commentVOList = CommentCache.getInstance().getcommentVOList();
        if (commentVOList == null){
            commentVOList = boardReadMapper.getComment();
            log.info("댓글 db 통신함");
            log.info("commentVOList: {}", commentVOList);
            CommentCache.getInstance().setCommentVOList(commentVOList);
        }
        commentVOList =
                commentVOList.stream()
                        .filter(vo -> vo.getBoardId() == writeId)
                        .filter(vo -> vo.getStatus() == 0)
                        .toList();

        log.info("가공 후 commentVOList: {}", commentVOList);

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
        String userId = LoginCache.getInstance().getLoginStatus().getUserId();
        String userName = LoginCache.getInstance().getLoginStatus().getUsername();
        log.info("BoardReadServiceImpl");
        log.info("userId: {}", userId);
        log.info("content: {}", content);
        log.info("boardId: {}", boardId);
        List<CommentVO> commentVOList = CommentCache.getInstance().getcommentVOList();
        int size = commentVOList.size() - 1;
        CommentVO commentVO = CommentVO.builder()
                .commentId(commentVOList.get(size).getCommentId()+1)
                .userId(userId)
                .userName(userName)
                .boardId(Math.toIntExact(boardId))
                .content(content)
                .createdAt(LocalDate.now().atStartOfDay())
                .status(0)
                .build();
        log.info("추가 이전 commentVOList : {}", commentVOList.size());
        commentVOList.add(commentVO);
        log.info("댓글 추가 완료");
        log.info("commentVOList: {}", commentVOList.size());
        CommentCache.getInstance().setCommentVOList(commentVOList);

        return boardReadMapper.writeComment(content, boardId, userId);
    }

    @Override
    public int deleteBoard(int writeId) {
        log.info("boardDelete(writeId) : {}", writeId);
        List<BoardVO> boardVOList = BoardCache.getInstance().getBoardVOList();
        if (boardVOList == null){
            boardVOList = boardMapper.getBoardList();
        }
        boardVOList.removeIf(vo -> vo.getWriteId().equals(String.valueOf(writeId)));
        BoardCache.getInstance().setBoardVOList(boardVOList);
        return boardReadMapper.deleteBoard(writeId);
    }

    @Override
    public int deleteComment(int commentId) {
        log.info("commentDelete(commentId) : {}", commentId);
        List<CommentVO> commentVOList = CommentCache.getInstance().getcommentVOList();
        if (commentVOList == null){
            commentVOList = boardReadMapper.getComment();
        }
        commentVOList.removeIf(vo -> vo.getCommentId() == commentId);
        CommentCache.getInstance().setCommentVOList(commentVOList);
        log.info("댓글 캐시 삭제완료 commentVOList: {}", commentVOList);
        return boardReadMapper.deleteComment(commentId);
    }
}
