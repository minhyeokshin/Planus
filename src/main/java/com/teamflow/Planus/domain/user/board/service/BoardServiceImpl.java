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
                        .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
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

    @Override
    public List<BoardDTO> searchBoardList(String boardId, String searchType, String keyword) {
        List<BoardDTO> boardDTOList = getBoardList(boardId);
        log.info("서비스 keyword: {}, searchType: {}", keyword, searchType);
        String searchWord = keyword.trim();

        if (!searchWord.isEmpty()) {
            if (searchType.equals("title")) {
                boardDTOList =
                        boardDTOList.stream()
                                .filter(dto -> dto.getTitle().equals(searchWord))
                                .toList();
            } else if (searchType.equals("userName")) {
                boardDTOList =
                        boardDTOList.stream()
                                .filter(dto -> dto.getUserName().equals(searchWord))
                                .toList();
            } else if (searchType.equals("writeNo")) {
                boardDTOList =
                        boardDTOList.stream()
                                .filter(dto -> dto.getWriteId() == Integer.parseInt(searchWord))
                                .toList();
            }
        }
        log.info("검색 사이즈 : {}", boardDTOList.size());
        return boardDTOList;
    }
}
