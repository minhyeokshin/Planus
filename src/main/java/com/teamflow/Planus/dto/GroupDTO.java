package com.teamflow.Planus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private Long groupId;
    private String groupName;
    private String groupEmail;
    private String gitHubRepo;
    private String gitHubOwner;
    private String gitHubToken;
    private LocalDateTime gitHubTokenDate;
}
