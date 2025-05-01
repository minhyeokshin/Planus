package com.teamflow.Planus.domain.user.board.service;

import com.teamflow.Planus.domain.user.board.mapper.BoardMapper;
import com.teamflow.Planus.dto.BoardDTO;
import com.teamflow.Planus.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public List<BoardDTO> getBoardList(int boardId) {
        List<BoardVO> boardVOList = boardMapper.getBoardList(boardId);
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
        return boardDTOList;
    }
}
