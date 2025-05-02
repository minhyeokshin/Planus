package com.teamflow.Planus.domain.user.dashboard.Service;

import com.teamflow.Planus.dto.CalendarDTO;
import com.teamflow.Planus.dto.CalendarStatsDTO;

import java.util.List;
import java.util.Map;

public interface DashBoardService {
    Map<String, List<CalendarDTO>> getCalendarList();
    List<String> getUserNameList();
    Map<String,CalendarStatsDTO> getCalendarStatsList();
}
