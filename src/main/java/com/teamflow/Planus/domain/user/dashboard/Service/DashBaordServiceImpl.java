package com.teamflow.Planus.domain.user.dashboard.Service;

import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.calendar.service.CalendarService;
import com.teamflow.Planus.domain.user.dashboard.Mapper.DashBoardMapper;
import com.teamflow.Planus.dto.CalendarDTO;
import com.teamflow.Planus.dto.CalendarStatsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class DashBaordServiceImpl implements DashBoardService {

    private final CalendarService calendarService;
    private final DashBoardMapper dashBoardMapper;

    @Override
    public Map<String, List<CalendarDTO>> getCalendarList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        List<CalendarDTO> calendarDTOList = calendarService.getTodayCalendarList();
        log.info("calendarDTOList: {}", calendarDTOList);

        Map<String, List<CalendarDTO>> calendarMap =
        calendarDTOList.stream()
                .filter(dto -> Objects.equals(dto.getGroupId(), currentUser.getGroupId()))
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        List<String> userNameList = dashBoardMapper.getUserNameList(currentUser.getGroupId()).stream()
                        .map(String::valueOf)
                                .map(String::trim)
                                        .distinct()
                                                .toList();
        log.info("서비스 userNameList: {}", userNameList);
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
