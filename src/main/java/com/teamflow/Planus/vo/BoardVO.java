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
public class BoardVO {
    private int writeId;
    private Long groupId;
    private String boardId;
    private String userId;
    private String userName;
    private LocalDateTime createdAt;
    private String title;
    private String content;
    private int status;
}
