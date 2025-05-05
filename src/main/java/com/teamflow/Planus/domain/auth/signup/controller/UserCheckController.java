package com.teamflow.Planus.domain.auth.signup.controller;

import com.teamflow.Planus.domain.auth.signup.service.UserCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserCheckController {

    private final UserCheckService userService;

    @GetMapping("/check-userid")
    public Map<String, Boolean> checkUserId(@RequestParam String userId) {
        boolean exists = userService.existsByUserId(userId);
        return Map.of("exists", exists);
    }

    @GetMapping("/check-groupId")
    public Map<String, Boolean> checkGroupName(@RequestParam Long groupId) {
        boolean exists = userService.excistsByGroupId(groupId);
        return Map.of("exists", exists);
    }


}
