package com.teamflow.Planus.domain.user.board.service;

import com.teamflow.Planus.cache.BoardCache;
import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.board.mapper.BoardMapper;
import com.teamflow.Planus.dto.BoardDTO;
import com.teamflow.Planus.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public List<BoardDTO> getBoardList(String boardId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        List<BoardVO> boardVOList = BoardCache.getInstance().getBoardVOList();
        if (boardVOList == null){
            boardVOList = boardMapper.getBoardList();
            BoardCache.getInstance().setBoardVOList(boardVOList);
            log.info("게시판 리스트 db 통신함");
            log.info("boardVO size: {}", boardVOList.size());
            for (BoardVO boardVO : boardVOList) {
                log.info("boardVO: {}", boardVO.getGroupId() + " " + currentUser.getGroupId() + " " + Objects.equals(boardVO.getGroupId(), currentUser.getGroupId()) );
            }
        }

        boardVOList =
                boardVOList.stream()
                        .filter(dto -> Objects.equals(dto.getGroupId(), currentUser.getGroupId()))
                        .toList();

        log.info("가공후 boardVOList size: {}", boardVOList.size());

        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (BoardVO boardVO : boardVOList) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .writeId(boardVO.getWriteId())
                    .boardId(boardVO.getBoardId())
                    .userId(boardVO.getUserId())
                    .userName(boardVO.getUserName())
                    .createdAt(boardVO.getCreatedAt())
                    .title(boardVO.getTitle())
                    .content(boardVO.getContent())
                    .build();
            boardDTOList.add(boardDTO);
        }

        boardDTOList =
        boardDTOList.stream()
                .filter(dto -> dto.getBoardId().equals(boardId))
                .toList();

        return boardDTOList;
    }
}
