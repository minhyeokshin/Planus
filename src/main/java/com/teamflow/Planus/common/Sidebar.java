package com.teamflow.Planus.common;

import com.teamflow.Planus.dto.MenuDTO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class Sidebar {

    @ModelAttribute("groupId")
    public Long getGroupId() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
//        Long groupId = userDetails.getGroupId();
        Long groupId = 1L;
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
