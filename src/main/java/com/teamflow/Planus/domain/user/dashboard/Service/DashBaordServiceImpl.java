package com.teamflow.Planus.domain.user.dashboard.Service;

import com.teamflow.Planus.domain.user.calendar.service.CalendarService;
import com.teamflow.Planus.domain.user.dashboard.Mapper.DashBoardMapper;
import com.teamflow.Planus.dto.CalendarDTO;
import com.teamflow.Planus.dto.CalendarStatsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class DashBaordServiceImpl implements DashBoardService {

    private final CalendarService calendarService;
    private final DashBoardMapper dashBoardMapper;

    @Override
    public Map<String, List<CalendarDTO>> getCalendarList() {
        List<CalendarDTO> calendarDTOList = calendarService.getTodayCalendarList();
        log.info("calendarDTOList: {}", calendarDTOList);
        Map<String, List<CalendarDTO>> calendarMap =
        calendarDTOList.stream()
                .map(dto -> {
                    if(dto.getStatus() == 1){
                        dto.setTitle("<s>" + dto.getTitle() + "</s>");
                    }
                    return dto;
                })
                .collect(Collectors.groupingBy(CalendarDTO::getUserName));
        log.info("calendarMap: {}", calendarMap);


        return calendarMap;
    }

    @Override
    public List<String> getUserNameList() {
        List<String> userNameList = dashBoardMapper.getUserNameList().stream()
                        .map(String::valueOf)
                                .map(String::trim)
                                        .distinct()
                                                .toList();
        log.info("userNameList: {}", userNameList);
        return userNameList;
    }

    @Override
    public Map<String, CalendarStatsDTO> getCalendarStatsList() {
        Map<String,CalendarStatsDTO> calendarStatsDTOMap = new HashMap<>();
        Map<String, List<CalendarDTO>> calendarMap = getCalendarList();

        for (Map.Entry<String, List<CalendarDTO>> entry : calendarMap.entrySet()) {
            String userName = entry.getKey().trim();
            List<CalendarDTO> calendars = entry.getValue();

            int total = calendars.size();
            int done = (int) calendars.stream().filter(c -> c.getStatus() == 1).count();
            int todo = total - done;
            double rate = total == 0 ? 0.0 : (done * 100.0) / total;

            CalendarStatsDTO stats = new CalendarStatsDTO();
            stats.setTotal(total);
            stats.setDone(done);
            stats.setTodo(todo);
            stats.setRate(rate);

            calendarStatsDTOMap.put(userName, stats);
        }
        log.info("calendarStatsDTOMap: {}", calendarStatsDTOMap);
        return calendarStatsDTOMap;
    }
}
