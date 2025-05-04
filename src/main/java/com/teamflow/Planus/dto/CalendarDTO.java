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
public class CalendarDTO {
    private String calendarId;
    private String title;
    private String content;
    private String userId;
    private String userName;
    private String color;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int status;
}
