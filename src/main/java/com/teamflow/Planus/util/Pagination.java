package com.teamflow.Planus.util;

import org.springframework.ui.Model;

import java.util.List;

public class Pagination {

    public static <T> void paginate(Model model, List<T> fullList, int page, String bodyPath) {
        int pageSize = 10;
        int totalItems = fullList.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalItems);

        List<T> pagedList = fullList.subList(start, end);
        System.out.println("▶ 잘린 리스트 크기: " + pagedList.size());


        model.addAttribute("List", pagedList); // 필요 시 변경 가능
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("body", bodyPath);
    }

}
