package com.teamflow.Planus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BoardViewLogDTO {
    private String viewLogId;
    private String userId;
    private String userName;
    private int writeId;
    private LocalDateTime viewTime;
}
