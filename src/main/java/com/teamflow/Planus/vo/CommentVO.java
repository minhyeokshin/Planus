package com.teamflow.Planus.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
    private int commentId;
    private String userId;
    private String userName;
    private int boardId;
    private String content;
    private int status;
    private LocalDateTime createdAt;
}
