package com.teamflow.Planus.common.service;

import com.teamflow.Planus.dto.MenuDTO;

import java.util.List;

public interface MenuService {
    List<MenuDTO> getMenuList(Long groupId);
    int insertMenuList(String menuName,String description);
    int deleteMenu(String menuName);
    int updateMenu(String menuName,Long menuId);
}
