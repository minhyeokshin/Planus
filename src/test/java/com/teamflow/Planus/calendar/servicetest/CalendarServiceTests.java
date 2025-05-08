package com.teamflow.Planus.calendar.servicetest;

import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.calendar.service.CalendarService;
import com.teamflow.Planus.vo.CalendarVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Log4j2
@SpringBootTest
public class CalendarServiceTests {

    @Autowired
    private CalendarService calendarService;

    @BeforeEach
    @Transactional
    void setup() {
        // 테스트용 가짜 UserDetails 객체 생성
        CustomUserDetails testUser = new CustomUserDetails();
        testUser.setRole("USER");
        testUser.setUserId("596fd21f-b6df-42f0-af8b-b69ce32ea7f3");
        testUser.setPassword("password");
        testUser.setName("Test User");
        testUser.setGroupId(177687393623609344L);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }



    @Test
    @DisplayName("캘린더 작성 테스트")
    @Transactional
    public void writeCalendar(){
        CalendarVO calendarVO = CalendarVO.builder()
                .calendarId("test-calendar-id")
                .title("테스트 일정 제목")
                .content("테스트 일정 내용입니다.")
                .userId("1e31be5f-09d8-4490-aea8-d29198cfa64c")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .groupId(178474446095388672L)
                .build();
        calendarService.writeCalendar("테스트 일정 제목","테스트 일정 내용입니다.",LocalDateTime.now(),LocalDateTime.now());
    }


    @Test
    @DisplayName("캘린더 삭제 테스트")
    @Transactional
    public void deleteCalendar(){
        String calendarId = "1";
        String userId = "1";
        calendarService.deleteCalendar(calendarId);
    }

    @Test
    @DisplayName("캘린더 업데이트")
    @Transactional
    public void updateCalendar(){
        String calendarId = "1";
        String userId = "1";
        int status = 1;
        calendarService.updateCalendar(calendarId,status);
    }

    @Test
    @DisplayName("캘린더 읽어오기 테스트")
    public void readCalendar(){
        List<Map<String, Object>> calendarDTOList =  calendarService.getCalendarList();
        log.info("calendarDTOList: {}", calendarDTOList);
    }
}
