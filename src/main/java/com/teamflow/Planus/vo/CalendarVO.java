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
public class CalendarVO {
    private String calendarId;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int status;
}
