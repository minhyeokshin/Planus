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
public class PullRequestVO {
    private String writeId;
    private String boardId;
    private String userId;
    private LocalDateTime createdAt;
    private String title;
    private String content;
}
