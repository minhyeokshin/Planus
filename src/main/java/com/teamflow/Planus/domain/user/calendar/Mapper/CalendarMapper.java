package com.teamflow.Planus.domain.user.calendar.Mapper;

import com.teamflow.Planus.dto.CalendarDTO;
import com.teamflow.Planus.vo.CalendarVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {
    int write(CalendarVO calendarVO);
    int delete(String calendarId,String userId);
    int update(String calendarId,int status,String userId);
    List<CalendarDTO> getCalendarList();
}
