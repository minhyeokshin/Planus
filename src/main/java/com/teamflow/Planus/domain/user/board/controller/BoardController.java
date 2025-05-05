package com.teamflow.Planus.domain.user.board.controller;

import com.teamflow.Planus.domain.user.board.service.BoardService;
import com.teamflow.Planus.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/group/{groupId}/board-{boardId}")
    public String board(@PathVariable("groupId") Long groupId,
                        @PathVariable("boardId") String boardId, Model model) {
        List<BoardDTO> boardDTOList = boardService.getBoardList(boardId);
        model.addAttribute("boardList", boardDTOList);
        return "user/pages/board/board";
    }

}
