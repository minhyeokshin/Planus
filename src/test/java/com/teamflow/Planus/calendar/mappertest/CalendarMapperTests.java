package com.teamflow.Planus.calendar.mappertest;

import com.teamflow.Planus.domain.user.calendar.Mapper.CalendarMapper;
import com.teamflow.Planus.dto.CalendarDTO;
import com.teamflow.Planus.vo.CalendarVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MapperScan(basePackages = "com.teamflow.Planus.domain.user.calendar.mapper")
public class CalendarMapperTests {

    @Autowired
    private CalendarMapper calendarMapper;


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
        calendarMapper.write(calendarVO);
    }


    @Test
    @DisplayName("캘린더 삭제 테스트")
    @Transactional
    public void deleteCalendar(){
        String calendarId = "1";
        String userId = "1";
        calendarMapper.delete(calendarId,userId);
    }

    @Test
    @DisplayName("캘린더 업데이트")
    @Transactional
    public void updateCalendar(){
        String calendarId = "1";
        String userId = "1";
        int status = 1;
        calendarMapper.update(calendarId,status,userId);
    }

    @Test
    @DisplayName("캘린더 읽어오기 테스트")
    public void readCalendar(){
        List<CalendarDTO> calendarDTOList =  calendarMapper.getCalendarList();
        log.info("calendarDTOList: {}", calendarDTOList);
    }
}
