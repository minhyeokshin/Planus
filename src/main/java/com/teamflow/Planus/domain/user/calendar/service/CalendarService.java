package com.teamflow.Planus.domain.user.calendar.service;

import com.teamflow.Planus.dto.CalendarDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CalendarService {
    List<Map<String,Object>> getCalendarList();

    List<CalendarDTO> getTodayCalendarList();

    int writeCalendar(String title, String content, LocalDateTime startDate, LocalDateTime endDate);

    int deleteCalendar(String calendarId);

    int updateCalendar(String calendarId,int status);
}
