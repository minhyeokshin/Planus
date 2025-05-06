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
public class BoardViewLogVO {
    private String viewLogId;
    private String userId;
    private String userName;
    private int writeId;
    private LocalDateTime viewTime;
}
