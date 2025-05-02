package com.teamflow.Planus.domain.user.dashboard.Controller;

import com.teamflow.Planus.domain.user.dashboard.Service.DashBoardService;
import com.teamflow.Planus.dto.CalendarDTO;
import com.teamflow.Planus.dto.CalendarStatsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping("/user/pages/index")
    public String index(Model model) {

        Map<String, List<CalendarDTO>> calendarMap = dashBoardService.getCalendarList();
        log.info("calendarMap: {}", calendarMap);
        model.addAttribute("calendarMap", calendarMap);

        List<String> userNameList = dashBoardService.getUserNameList();
        model.addAttribute("userNameList", userNameList);

        Map<String, CalendarStatsDTO> calendarStatsMap = dashBoardService.getCalendarStatsList();
        model.addAttribute("calendarStatsMap", calendarStatsMap);
        log.info("calendarStatsMap: {}", calendarStatsMap);

        for (String userName : userNameList) {
            log.info("Key Matching: {}, Exists: {}", userName, calendarStatsMap.containsKey(userName));
        }

        return "user/pages/index"; }
}
