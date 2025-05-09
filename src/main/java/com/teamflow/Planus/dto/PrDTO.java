package com.teamflow.Planus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PrDTO {
    private String prId;
    private String prTitle;
    private Long groupId;
    private String userName;
    private String userEmail;
    private LocalDateTime prDate;
    private String prStatus;
    private String prURL;
}
