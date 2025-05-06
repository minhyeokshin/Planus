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
public class CommentDTO {
    private int commentId;
    private Long groupId;
    private String userId;
    private String userName;
    private int boardId;
    private String content;
    private LocalDateTime createdAt;
}
