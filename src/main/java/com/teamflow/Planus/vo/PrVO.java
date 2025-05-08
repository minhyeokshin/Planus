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
public class PrVO {
    private String prId;
    private String prTitle;
    private Long groupId;
    private String userName;
    private String userEmail;
    private LocalDateTime prDate;
    private String prURL;
    private String prStatus;
}
