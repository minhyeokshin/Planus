package com.teamflow.Planus.domain.user.api.controller;

import com.teamflow.Planus.domain.user.api.service.BoardReadService;
import com.teamflow.Planus.dto.BoardDTO;
import com.teamflow.Planus.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user/pages/api")
@RequiredArgsConstructor
public class BoardReadController {

    private final BoardReadService boardService;

    @GetMapping("/boardRead/{boardId}")
    public String boardRead(@PathVariable int boardId, Model model) {
        BoardDTO board = boardService.findById(boardId);
        List<CommentDTO> commentList = boardService.getComment(boardId);
        model.addAttribute("board", board);
        model.addAttribute("commentList", commentList);
        return "user/pages/api/boardRead";
    }

    @PostMapping("/commentWrite")
    public String  boardWrite(@RequestParam String content,
                             @RequestParam Long boardId) {
        boardService.writeComment(content, boardId);
        System.out.println("content: " + content);
        System.out.println("boardId: " + boardId);
        return "redirect:/user/pages/api/boardRead/" + boardId;
    }

    @PostMapping("/boardDelete")
    public String boardDelete(@RequestParam int writeId,
                                 @RequestParam int boardId) {
        log.info("boardDelete(writeId) : {}", writeId);
        log.info("boardId: {}", boardId);
        boardService.deleteBoard(writeId);
        return "redirect:/user/pages/board/board-" + boardId;
    }

    @PostMapping("/commentDelete")
    public String commentDelete(@RequestParam int commentId,
                                @RequestParam int boardId) {
        boardService.deleteComment(commentId);
        return "redirect:/user/pages/api/boardRead/" + boardId;
    }
}