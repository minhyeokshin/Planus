package com.teamflow.Planus.domain.auth.login.controller;

import com.teamflow.Planus.domain.auth.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                            @RequestParam String password) {
        log.info("username: {}", username);
        log.info("password: {}", password);

        int result = loginService.login(username, password);

        if (result == 0) {
            return "redirect:/common/pages/login";
        }

        return "redirect:/user/pages/index";

    }
}
