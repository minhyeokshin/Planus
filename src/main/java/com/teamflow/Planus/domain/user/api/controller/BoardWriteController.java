package com.teamflow.Planus.domain.user.api.controller;

import com.teamflow.Planus.domain.user.api.service.BoardWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/pages/api")
@Log4j2
public class BoardWriteController {

    private final BoardWriteService boardWriteService;

    // ✅ 글쓰기 처리
    @PostMapping("/group/{groupId}/board/{boardId}/write")
    public String boardWrite(@RequestParam String title,
                             @RequestParam String content,
                             @PathVariable Long groupId,
                             @PathVariable Long boardId,
                             @RequestParam("redirectUrl")String redirectUrl) {
        log.info("groupId: {}", groupId);
        log.info("게시판 ID: {}", boardId);
        log.info("게시글 이름 : {}", title);
        log.info("본문 내용 : {}" , content);
        log.info("referer: {}", redirectUrl);
        boardWriteService.write(title, content, boardId);
        return "redirect:" + (redirectUrl != null ? redirectUrl : "board");
    }

    // ✏️ 글쓰기 폼 페이지
    @GetMapping("/group/{groupId}/write/boardId/{boardId}")
    public String showWritePage(@PathVariable Long groupId,
                                @PathVariable Long boardId,
                                Model model) {
        model.addAttribute("groupId", groupId);
        model.addAttribute("boardId", boardId);
        return "user/pages/api/boardwrite"; // 타임리프 HTML 경로
    }
}