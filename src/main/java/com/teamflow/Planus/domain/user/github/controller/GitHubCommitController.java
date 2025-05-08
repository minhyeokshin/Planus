package com.teamflow.Planus.domain.user.github.controller;

import com.teamflow.Planus.domain.user.github.service.GitHubCommitService;
import com.teamflow.Planus.vo.CommitVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class GitHubCommitController {

    private final GitHubCommitService gitHubCommitService;

    @GetMapping("/user/pages/board/commitList")
    public String commitList(Model model) {
        List<CommitVO> commitList = gitHubCommitService.getCommitList();
        log.info("commitList: {}", commitList);
        log.info("commitSize : {}", commitList.size());
        model.addAttribute("commitList", commitList);
        return "user/pages/board/commitList";
    }
}
