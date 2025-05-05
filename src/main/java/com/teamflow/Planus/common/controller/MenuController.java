package com.teamflow.Planus.common.controller;

import com.teamflow.Planus.common.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/menu-insert")
    public String insertMenuList(@RequestParam String menuName, @RequestParam String description) {
        menuService.insertMenuList(menuName,description);
        return "redirect:/user/pages/api/menuManagement";
    }

    @PostMapping("/menu-delete")
    public String deleteMenu(@RequestParam String menuName) {
        menuService.deleteMenu(menuName);
        return "redirect:/user/pages/api/menuManagement";
    }

    @PostMapping("/menu-rename")
    public String renameMenu(@RequestParam String menuName,
                             @RequestParam Long menuId) {
        menuService.updateMenu(menuName,menuId);
        return "redirect:/user/pages/api/menuManagement";
    }
}
