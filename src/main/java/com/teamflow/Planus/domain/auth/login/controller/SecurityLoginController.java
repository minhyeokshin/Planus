package com.teamflow.Planus.domain.auth.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityLoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
