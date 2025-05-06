package com.teamflow.Planus.common.mapper;

import com.teamflow.Planus.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    int insertMenuList(MenuDTO menuDTO);
    List<MenuDTO> getMenuList(Long groupId);
    int deleteMenu(String menuName,Long groupId);
    int updateMenu(String menuName,Long menuId,Long groupId);
}
