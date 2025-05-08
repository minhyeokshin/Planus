package com.teamflow.Planus.domain.user.github.controller;

import com.teamflow.Planus.domain.user.github.service.GitHubIssueService;
import com.teamflow.Planus.dto.IssueDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class GitHubIssueController {

    private final GitHubIssueService gitHubIssueService;

    @GetMapping("/user/pages/board/issueList")
    public String issueList(Model model) {
        List<IssueDTO> issueList = gitHubIssueService.getIssueList();
        log.info("issueList: {}", issueList);
        log.info("issueSize : {}", issueList.size());
        model.addAttribute("issueList", issueList);
        return "user/pages/board/issueList";
    }
}
