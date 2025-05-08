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
public class CommitDTO {
    private String commitId;
    private String commitMsg;
    private Long groupId;
    private String userName;
    private String userEmail;
    private LocalDateTime commitDate;
    private String commitURL;
}
