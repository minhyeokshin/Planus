package com.teamflow.Planus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private String writeId;
    private String boardId;
    private String userId;
    private String userName;
    private LocalDateTime createdAt;
    private String title;
    private String content;
}
