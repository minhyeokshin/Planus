package com.teamflow.Planus.common.controller;

import com.teamflow.Planus.common.service.MenuService;
import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class SidebarController {

    private final MenuService menuService;

    @ModelAttribute("groupId")
    public Long getGroupId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            log.warn("userDetails is null");
            return null;
        }

        Long groupId = userDetails.getGroupId();
        log.info("groupId: {}", groupId);
        return groupId;
    }

    @ModelAttribute("menuList")
    public List<MenuDTO> getMenuList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            log.warn("userDetails is null, returning empty menu list");
            return List.of(); // 빈 리스트 반환
        }
        List<MenuDTO> menuList = menuService.getMenuList(userDetails.getGroupId());
        return menuList;
    }

}
