package com.teamflow.Planus.domain.user.calendar.service;

import com.teamflow.Planus.cache.LoginCache;
import com.teamflow.Planus.domain.user.calendar.Mapper.CalendarMapper;
import com.teamflow.Planus.dto.CalendarDTO;
import com.teamflow.Planus.vo.CalendarVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class CalendarServiceImpl implements CalendarService {

    private final CalendarMapper calendarMapper;

    @Override
    public List<Map<String,Object>> getCalendarList() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<CalendarDTO> calendarDTOList = calendarMapper.getCalendarList();

        for (CalendarDTO calendarDTO : calendarDTOList) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", calendarDTO.getUserId());
            map.put("title", calendarDTO.getTitle());
            map.put("start", calendarDTO.getStartDate().toString());
            map.put("end", calendarDTO.getEndDate().toString());
            map.put("calendarId", calendarDTO.getCalendarId());
            map.put("status", calendarDTO.getStatus());
            map.put("content", calendarDTO.getContent());
            map.put("userName", calendarDTO.getUserName());
            map.put("allDay", false);
            result.add(map);
        }

        return result;
    }

    @Override
    public List<CalendarDTO> getTodayCalendarList() {
        List<CalendarDTO> calendarDTOList = calendarMapper.getCalendarList();

        calendarDTOList
                = calendarDTOList.stream()
                .filter(dto -> dto.getStartDate().isBefore(LocalDateTime.now()))
                .filter(dto -> dto.getEndDate().isAfter(LocalDateTime.now()))
                .toList();

        return calendarDTOList;
    }

    @Override
    public int writeCalendar(String title, String content, LocalDateTime startDate, LocalDateTime endDate) {

        String userId = LoginCache.getInstance().getLoginStatus().getUserId();

        CalendarVO calendarVO = CalendarVO.builder()
                .calendarId(UUID.randomUUID().toString())
                .title(title)
                .content(content)
                .userId(userId)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        int result = calendarMapper.write(calendarVO);
        return result;
    }

    @Override
    public int deleteCalendar(String calendarId) {
        String userId = LoginCache.getInstance().getLoginStatus().getUserId();
        int result = calendarMapper.delete(calendarId,userId);
        return result;
    }

    @Override
    public int updateCalendar(String calendarId, int status) {
        String userID = LoginCache.getInstance().getLoginStatus().getUserId();
        log.info("userID: {}", userID);
        int result = calendarMapper.update(calendarId, status,userID);
        return result;
    }
}
