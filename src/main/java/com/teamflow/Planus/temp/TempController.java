package com.teamflow.Planus.temp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/pages")
public class TempController {

    @GetMapping("/index")
    public String adminIndex() {
        return "user/pages/index";
    }

    @GetMapping("/convention/commit")
    public String commit() { return "user/pages/convention/commit"; }

    @GetMapping("/convention/pr")
    public String pr() { return "user/pages/convention/pr"; }

    @GetMapping("/convention/issue")
    public String issue() { return "user/pages/convention/issue"; }

    @GetMapping("/calendar/calendar")
    public String calendar() { return "user/pages/calendar/calendar"; }

    @GetMapping("/board/board-1")
    public String board1() { return "user/pages/board/board-1"; }

    @GetMapping("board/board-2")
    public String board2() { return "user/pages/board/board-2"; }

    @GetMapping("board/board-3")
    public String board3() { return "user/pages/board/board-3"; }


}