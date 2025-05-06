package com.teamflow.Planus.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class GroupVO {
    private Long groupId;
    private String groupName;
    private String groupEmail;
}
