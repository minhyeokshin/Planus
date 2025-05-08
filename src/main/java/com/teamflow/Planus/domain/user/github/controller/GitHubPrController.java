package com.teamflow.Planus.domain.user.github.controller;

import com.teamflow.Planus.domain.user.github.service.GitHubPrService;
import com.teamflow.Planus.dto.PrDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class GitHubPrController {

    private final GitHubPrService gitHubPrService;

    @GetMapping("/user/pages/board/prList")
    public String prList(Model model) {
        List<PrDTO> prList = gitHubPrService.getPrList();
        log.info("prList: {}", prList);
        log.info("prSize : {}", prList.size());
        model.addAttribute("prList", prList);
        return "user/pages/board/prList";
    }
}
