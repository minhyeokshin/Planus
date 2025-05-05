package com.teamflow.Planus.common.controller;

import com.teamflow.Planus.common.service.MenuService;
import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class SidebarController {

    private final MenuService menuService;

    @ModelAttribute("groupId")
    public Long getGroupId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Long groupId = userDetails.getGroupId();
        log.info("groupId: {}", groupId);
        return groupId;
    }

    @ModelAttribute("menuList")
    public List<MenuDTO> getMenuList() {
        List<MenuDTO> menuList = menuService.getMenuList();
        return menuList;
    }

}
