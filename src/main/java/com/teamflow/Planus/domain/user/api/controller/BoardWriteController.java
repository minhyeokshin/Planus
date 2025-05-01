package com.teamflow.Planus.domain.user.api.controller;

import com.teamflow.Planus.domain.user.api.service.BoardWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/pages/api")
@Log4j2
public class BoardWriteController {

    private final BoardWriteService boardWriteService;

    @PostMapping("/write")
    public String boardWrite(@RequestParam String title,
                             @RequestParam String content,
                             @RequestParam Long boardId,
                             @RequestParam("redirectUrl")String redirectUrl) {
        log.info("게시판 ID: {}", boardId);
        log.info("게시글 이름 : {}", title);
        log.info("본문 내용 : {}" , content);
        log.info("referer: {}", redirectUrl);
        boardWriteService.write(title, content, boardId);
        return "redirect:" + (redirectUrl != null ? redirectUrl : "/user/pages/board/board-1");
    }
}