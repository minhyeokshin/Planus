package com.teamflow.Planus.domain.auth.signup.controller;

import com.teamflow.Planus.domain.auth.signup.service.GroupCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GroupCheckController {

    private final GroupCheckService groupCheckService;

    @GetMapping("/check-groupName")
    public Map<String, Boolean> checkUserId(@RequestParam String groupName) {
        boolean exists =groupCheckService.existsByGroupName(groupName);
        return Map.of("exists", exists);
    }
}
