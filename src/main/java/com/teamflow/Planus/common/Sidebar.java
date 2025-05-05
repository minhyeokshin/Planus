package com.teamflow.Planus.common;

import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.dto.MenuDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@ControllerAdvice
public class Sidebar {

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
        List<MenuDTO> menuList = new ArrayList<>();
        menuList.add(new MenuDTO("공지사항", "/board-1",1));
        menuList.add(new MenuDTO("회의록", "/board-2",2));
        menuList.add(new MenuDTO("자유게시판", "/board-3",3));
        return menuList;
    }
}
