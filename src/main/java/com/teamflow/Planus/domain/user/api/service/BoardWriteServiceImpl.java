package com.teamflow.Planus.domain.user.api.service;

import com.teamflow.Planus.domain.user.api.mapper.BoardWriteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardWriteServiceImpl implements BoardWriteService {

    private final BoardWriteMapper boardWriteMapper;
    @Override
    public int write(String title, String content, Long boardId) {
        log.info("BoardWriteServiceImpl");
        log.info("boardId: {}", boardId);
        log.info("title: {}", title);
        log.info("content: {}", content);
        String userId = "user-1";
        int result = boardWriteMapper.write(title, content, boardId,userId);
        return result;
    }
}
