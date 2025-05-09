package com.teamflow.Planus.domain.user.calendar.controller;

import com.teamflow.Planus.domain.user.calendar.service.CalendarService;
import com.teamflow.Planus.dto.CalendarDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CalendarController {

    private final CalendarService calendarService;

    // 오늘의 할일
    @GetMapping("/user/pages/calendar/calendar")
    public String getCalendarPage(Model model) {

        List<CalendarDTO> todayTasks = calendarService.getTodayCalendarList();


        model.addAttribute("todayTasks", todayTasks);
        return "user/pages/calendar/calendar";
    }

    // 풀캘린더 리스트
    @GetMapping("/api/schedule")
    @ResponseBody
    public List<Map<String, Object>> getFullCalendarEvents() {
        List<Map<String, Object>> result = calendarService.getCalendarList();
        return result;
    }

    @PostMapping("/api/success")
    @ResponseBody
    public String success(@RequestBody Map<String, Object> map) {
        String calendarId = (String) map.get("calendarId");
        int status = (int) map.get("status");
        int result = calendarService.updateCalendar(calendarId, status);
        log.info("완료 calendarId: {}", calendarId);
        log.info("완료 status: {}", status);
        String msg = "✅ 완료 처리되었습니다";
        if (result == 0) {
            msg = "요청이 실패하였습니다.";
        }
        log.info(msg);
        return msg;
    }

    @PostMapping("/api/fail")
    @ResponseBody
    public String fail(@RequestBody Map<String, Object> map) {
        String calendarId = (String) map.get("calendarId");
        int status = (int) map.get("status");
        int result = calendarService.updateCalendar(calendarId, status);
        log.info("미완료 calendarId: {}", calendarId);
        log.info("미완료 status: {}", status);
        String msg = "✅ 완료 처리되었습니다";
        if (result == 0) {
            msg = "요청이 실패하였습니다.";
        }
        log.info(msg);
        return msg;
    }

    @PostMapping("/api/delete")
    @ResponseBody
    public String delete(@RequestBody Map<String, Object> map) {
        String calendarId = (String) map.get("calendarId");
        int result = calendarService.deleteCalendar(calendarId);
        String msg = "✅ 완료 처리되었습니다";
        if (result == 0) {
            msg = "요청이 실패하였습니다.";
        }
        log.info(msg);
        return msg;
    }


//    @RequestMapping(value = "/api/getCalendarList", method = { RequestMethod.GET, RequestMethod.POST })
    @PostMapping("/api/insertSchedule")
    @ResponseBody
    public void insertSchedule(@RequestBody Map<String, Object> map) {
        log.info("일정등록 컨트롤러 진입");
        log.info("map: {}", map);
        String title = (String) map.get("title");
        String content = (String) map.get("content");
        LocalDateTime startDate = LocalDateTime.parse((String) map.get("startDate"));
        LocalDateTime endDate = LocalDateTime.parse((String) map.get("endDate"));
        calendarService.writeCalendar(title, content, startDate, endDate);
        log.info("title: {}", title);
        log.info("content: {}", content);
        log.info("startDate: {}", startDate);
        log.info("endDate: {}", endDate);

    }
}
