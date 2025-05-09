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
public class GroupVO {
    private Long groupId;
    private String groupName;
    private String groupEmail;
    private String gitHubRepo;
    private String gitHubOwner;
    private String gitHubToken;
    private LocalDateTime gitHubTokenDate;
}
