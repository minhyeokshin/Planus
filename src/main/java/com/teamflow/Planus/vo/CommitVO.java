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
public class CommitVO {
    private String commitId;
    private String commitMsg;
    private Long groupId;
    private String userName;
    private String userEmail;
    private LocalDateTime commitDate;
    private String commitURL;
}
