package com.teamflow.Planus.domain.user.api.service;

import com.teamflow.Planus.cache.BoardCache;
import com.teamflow.Planus.cache.CommentCache;
import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.api.mapper.BoardReadMapper;
import com.teamflow.Planus.domain.user.board.mapper.BoardMapper;
import com.teamflow.Planus.dto.BoardDTO;
import com.teamflow.Planus.dto.BoardViewLogDTO;
import com.teamflow.Planus.dto.CommentDTO;
import com.teamflow.Planus.vo.BoardVO;
import com.teamflow.Planus.vo.BoardViewLogVO;
import com.teamflow.Planus.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardReadServiceImpl implements BoardReadService {

    private final BoardReadMapper boardReadMapper;
    private final BoardMapper boardMapper;

    @Override
    public BoardDTO findById(int writeId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        List<BoardVO> boardVOList = BoardCache.getInstance().getBoardVOList();
        if (boardVOList == null){
            boardVOList = boardMapper.getBoardList();
            log.info("db 통신함");
            BoardCache.getInstance().setBoardVOList(boardVOList);
        }

        boardVOList =
                boardVOList.stream()
                        .filter(vo ->vo.getWriteId() == writeId)
                        .toList();

        BoardVO boardVO = boardVOList.get(0);

        if (!boardVO.getGroupId().equals(currentUser.getGroupId())){
            throw new AccessDeniedException("해당 게시글에 대한 권한이 없습니다.");
        }

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
            log.info("commentDTO:{}",commentDTO.getUserName());
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    @Override
    @Transactional
    public int writeComment(String content, Long boardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        String userId = currentUser.getUserId();
        String userName = currentUser.getName();

        log.info("댓글 작성자 이름 : {}", currentUser.getName());

        log.info("userName: {}", userName);
        log.info("BoardReadServiceImpl");
        log.info("userId: {}", userId);
        log.info("content: {}", content);
        log.info("boardId: {}", boardId);
        List<CommentVO> commentVOList = CommentCache.getInstance().getcommentVOList();
        int size = 0;
        int commentId = 1;

        if(!commentVOList.isEmpty()){
            size = commentVOList.size() - 1;
        }

        if(!commentVOList.isEmpty()){
            commentId = commentVOList.get(size).getCommentId() + 1;
        }

        CommentVO commentVO = CommentVO.builder()
                .commentId(commentId)
                .userId(userId)
                .userName(userName)
                .boardId(Math.toIntExact(boardId))
                .content(content)
                .groupId(currentUser.getGroupId())
                .createdAt(LocalDateTime.now())
                .status(0)
                .build();
        log.info("추가 이전 commentVOList : {}", commentVOList.size());
        commentVOList.add(commentVO);
        log.info("댓글 추가 완료");
        log.info("commentVOList: {}", commentVOList.size());
        CommentCache.getInstance().setCommentVOList(commentVOList);
        LocalDateTime now = LocalDateTime.now();

        return boardReadMapper.writeComment(content, boardId, userId,now,currentUser.getGroupId());
    }

    @Override
    @Transactional
    public int deleteBoard(int writeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        log.info("boardDelete(writeId) : {}", writeId);
        List<BoardVO> boardVOList = BoardCache.getInstance().getBoardVOList();
        if (boardVOList == null){
            boardVOList = boardMapper.getBoardList();
        }
        LocalDateTime now = LocalDateTime.now();
        int result = boardReadMapper.deleteBoard(writeId,now,currentUser.getUserId());
        if (result > 0){
            boardVOList.removeIf(vo -> vo.getWriteId() == writeId );
            BoardCache.getInstance().setBoardVOList(boardVOList);
        }
        return result;
    }

    @Override
    @Transactional
    public int deleteComment(int commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        log.info("commentDelete(commentId) : {}", commentId);
        List<CommentVO> commentVOList = CommentCache.getInstance().getcommentVOList();
        if (commentVOList == null){
            commentVOList = boardReadMapper.getComment();
        }

        LocalDateTime now = LocalDateTime.now();
        int result = boardReadMapper.deleteComment(commentId,now,currentUser.getUserId());
        if (result > 0){
            commentVOList.removeIf(vo -> vo.getCommentId() == commentId);
            CommentCache.getInstance().setCommentVOList(commentVOList);
            log.info("댓글 캐시 삭제완료 commentVOList: {}", commentVOList);
        }
        return result;
    }

    @Override
    public int viewLog(int writeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        String viewId = UUID.randomUUID().toString();
        String userId = currentUser.getUserId();
        LocalDateTime now = LocalDateTime.now();
        int result = boardReadMapper.viewLog(viewId, writeId, userId, now);
        return result;
    }

    @Override
    public List<BoardViewLogDTO> getViewLog(int writeId) {
        List<BoardViewLogDTO> boardViewLogDTOList = new ArrayList<>();
        List<BoardViewLogVO> boardViewLogVOList = boardReadMapper.getViewLog();
        for (BoardViewLogVO boardViewLogVO : boardViewLogVOList) {
            BoardViewLogDTO boardViewLogDTO = BoardViewLogDTO.builder()
                    .userId(boardViewLogVO.getUserId())
                    .userName(boardViewLogVO.getUserName())
                    .viewLogId(boardViewLogVO.getViewLogId())
                    .viewTime(boardViewLogVO.getViewTime())
                    .writeId(boardViewLogVO.getWriteId())
                    .build();
            boardViewLogDTOList.add(boardViewLogDTO);
        }

        boardViewLogDTOList =
                boardViewLogDTOList.stream()
                        .filter(dto -> dto.getWriteId() == writeId)
                        .toList();
        return boardViewLogDTOList;
    }
}
