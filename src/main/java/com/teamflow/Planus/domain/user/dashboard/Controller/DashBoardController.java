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
        log.info(calendarStatsMap.get("신민혁").getDone());
//        model.addAttribute("userNameList", List.of("테스트유저1", "테스트유저2"));
//        log.info("userNameList: {}", model.getAttribute("userNameList"));
//        Map<String, CalendarStatsDTO> testStats = new HashMap<>();
//        CalendarStatsDTO stats1 = new CalendarStatsDTO();
//        stats1.setTotal(10);
//        stats1.setDone(5);
//        stats1.setTodo(5);
//        stats1.setRate(50.0);
//        testStats.put("테스트유저1", stats1);
//
//        CalendarStatsDTO stats2 = new CalendarStatsDTO();
//        stats2.setTotal(8);
//        stats2.setDone(4);
//        stats2.setTodo(4);
//        stats2.setRate(50.0);
//        testStats.put("테스트유저2", stats2);
//
//        model.addAttribute("calendarStatsMap", testStats);
//        log.info(testStats.toString());

        for (String userName : userNameList) {
            log.info("Key Matching: {}, Exists: {}", userName, calendarStatsMap.containsKey(userName));
        }


        return "user/pages/index"; }
}
