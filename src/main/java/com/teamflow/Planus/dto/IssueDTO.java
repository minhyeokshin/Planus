package com.teamflow.Planus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {
    private String issueId;
    private String issueTitle;
    private Long groupId;
    private String issueStatus;
    private String issueURL;
    private String userName;
    private String userEmail;
    private LocalDateTime issueDate;
}
