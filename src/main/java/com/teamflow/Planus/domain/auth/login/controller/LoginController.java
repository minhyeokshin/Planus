package com.teamflow.Planus.domain.auth.login.controller;

import com.teamflow.Planus.domain.auth.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/common/pages")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login() { return "/common/pages/login"; }

    @PostMapping("/login/login")
    public String loginPost(@RequestParam String username,
                            @RequestParam String password,
                            RedirectAttributes redirectAttributes) {
        log.info("username: {}", username);
        log.info("password: {}", password);

        int result = loginService.login(username, password);

        if (result == 0) {
            String msg = "로그인에 실패 하였습니다.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:/common/pages/login";
        }

        return "redirect:/user/pages/index";

    }
}
