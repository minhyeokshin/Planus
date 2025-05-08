package com.teamflow.Planus.temp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/pages")
public class TempController {

//    @GetMapping("/index")
//    public String adminIndex() {
//        return "user/pages/index";
//    }

    @GetMapping("/convention/commit")
    public String commit() { return "user/pages/convention/commit"; }

    @GetMapping("/convention/pr")
    public String pr() { return "user/pages/convention/pr"; }

    @GetMapping("/convention/issue")
    public String issue() { return "user/pages/convention/issue"; }

//    @GetMapping("/calendar/calendar")
//    public String calendar() { return "user/pages/calendar/calendar"; }

//    @GetMapping("/board/commitList")
//    public String board1() { return "user/pages/board/commitList"; }

//    @GetMapping("/board/prList")
//    public String board2() { return "user/pages/board/prList"; }

    @GetMapping("/api/boardwrite")
    public String board3() { return "user/pages/api/boardwrite"; }

//    @GetMapping("/board/board")
//    public String board() { return "user/pages/board/board"; }

    @GetMapping("/api/boardRead")
    public String boardRead() { return "user/pages/api/boardRead"; }

    @GetMapping("/api/menuManagement")
    public String menuManagement() { return "user/pages/api/menuManagement"; }
}