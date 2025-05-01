package com.teamflow.Planus.domain.user.api.service;

import com.teamflow.Planus.cache.BoardCache;
import com.teamflow.Planus.cache.LoginCache;
import com.teamflow.Planus.domain.user.api.mapper.BoardWriteMapper;
import com.teamflow.Planus.domain.user.board.mapper.BoardMapper;
import com.teamflow.Planus.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardWriteServiceImpl implements BoardWriteService {

    private final BoardWriteMapper boardWriteMapper;
    private final BoardMapper boardMapper;

    @Override
    public int write(String title, String content, Long boardId) {
        log.info("BoardWriteServiceImpl");
        log.info("boardId: {}", boardId);
        log.info("title: {}", title);
        log.info("content: {}", content);
        String userId = LoginCache.getInstance().getLoginStatus().getUserId();
        String userName = LoginCache.getInstance().getLoginStatus().getUsername();
        List<BoardVO> boardVOList = BoardCache.getInstance().getBoardVOList();
        if (boardVOList == null){
            boardVOList = boardMapper.getBoardList();
            BoardCache.getInstance().setBoardVOList(boardVOList);
        }
        log.info("boardVOList: {}", boardVOList);
        int size = boardVOList.size() - 1;
        BoardVO boardVO = BoardVO.builder()
                .writeId(boardVOList.get(size).getWriteId() + 1)
                .boardId(String.valueOf(boardId))
                .userId(userId)
                .userName(userName)
                .createdAt(LocalDateTime.now())
                .title(title)
                .content(content)
                .status(0)
                .build();
        boardVOList.add(boardVO);
        BoardCache.getInstance().setBoardVOList(boardVOList);
        int result = boardWriteMapper.write(title, content, boardId,userId);
        return result;
    }
}
