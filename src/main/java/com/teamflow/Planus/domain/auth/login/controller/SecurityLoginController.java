package com.teamflow.Planus.domain.auth.login.controller;

import com.teamflow.Planus.domain.auth.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequiredArgsConstructor
public class SecurityLoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/password")
    public String password() {
        return "password";
    }

    @PostMapping("/password")
    public String passwordSubmit(@RequestParam String password,
                                 @RequestParam String newPassword,
                                 @RequestParam String newPasswordCheck) {
        log.info("password: {}, newPassword: {}, newPasswordCheck: {}", password, newPassword, newPasswordCheck);
        int result = loginService.passwordChange(password, newPassword, newPasswordCheck);
        log.info("pw change result: {}", result);

        return "redirect:/user/pages/index";
    }
}
