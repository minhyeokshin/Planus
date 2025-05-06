package com.teamflow.Planus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MenuDTO {
    private String menuName;
    private String menuPath;
    private int boardId;
    private Long groupId;
    private String description;
}
