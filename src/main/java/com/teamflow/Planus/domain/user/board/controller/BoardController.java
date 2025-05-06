package com.teamflow.Planus.domain.user.board.controller;

import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.board.service.BoardService;
import com.teamflow.Planus.dto.BoardDTO;
import com.teamflow.Planus.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/group/{groupId}/board-{boardId}")
    public String board(@PathVariable("groupId") Long groupId,
                        @PathVariable("boardId") String boardId,
                        @RequestParam(defaultValue = "1") int page,
                        Model model) {
        List<BoardDTO> boardDTOList = boardService.getBoardList(boardId);
        model.addAttribute("boardList", boardDTOList);
        Pagination.paginate(model, boardDTOList, page, "/user/group/" + groupId + "/board-" + boardId);
        return "user/pages/board/board";
    }

    @GetMapping("/api/search")
//    @RequestMapping(value = "/api/search", method = { RequestMethod.GET, RequestMethod.POST })
    public String search(@RequestParam(name = "boardId", required = false) String boardId,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam String searchType,
                                 @RequestParam String keyword,
                                 @AuthenticationPrincipal CustomUserDetails userDetails,
                                 Model model) {
        log.info("searchType: {}, keyword: {}", searchType, keyword);
        log.info("boardId: {}", boardId);
        List<BoardDTO> boardDTOList = boardService.searchBoardList(boardId, searchType, keyword);
        model.addAttribute("boardList", boardDTOList);
        String bodypath = "/user/api/search?searchType=" + searchType + "&keyword=" + keyword + "&boardId=" + boardId + "&groupId=" + userDetails.getGroupId();
        Pagination.paginate(model, boardDTOList, page, bodypath );
        return "user/pages/board/board";
    }
}
