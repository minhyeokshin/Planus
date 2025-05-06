package com.teamflow.Planus.common.service;

import com.teamflow.Planus.common.mapper.MenuMapper;
import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Override
    public List<MenuDTO> getMenuList(Long groupId) {

        List<MenuDTO> menuList = menuMapper.getMenuList(groupId);

        return menuList;
    }

    @Override
    public int insertMenuList(String menuName,String description) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        MenuDTO menuDTO = MenuDTO.builder()
                .groupId(currentUser.getGroupId())
                .menuName(menuName)
                .description(description)
                .build();

        Long groupId = currentUser.getGroupId();
        int result = menuMapper.insertMenuList(menuDTO);
        return result;
    }

    @Override
    public int deleteMenu(String menuName) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        int result = menuMapper.deleteMenu(menuName,currentUser.getGroupId());
        return result;
    }

    @Override
    public int updateMenu(String menuName, Long menuId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        Long groupId = currentUser.getGroupId();
        int result = menuMapper.updateMenu(menuName,menuId,groupId);
        return 0;
    }
}
