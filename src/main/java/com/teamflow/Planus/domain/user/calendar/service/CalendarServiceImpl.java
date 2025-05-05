package com.teamflow.Planus.domain.user.calendar.service;

import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.api.mapper.BoardWriteMapper;
import com.teamflow.Planus.domain.user.calendar.Mapper.CalendarMapper;
import com.teamflow.Planus.dto.CalendarDTO;
import com.teamflow.Planus.dto.PostDTO;
import com.teamflow.Planus.util.ColorMappingService;
import com.teamflow.Planus.util.MailService;
import com.teamflow.Planus.vo.CalendarVO;
import com.teamflow.Planus.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class CalendarServiceImpl implements CalendarService {

    private final CalendarMapper calendarMapper;
    private final MailService mailService;
    private final BoardWriteMapper boardWriteMapper;
    private final ColorMappingService colorMappingService;

    @Override
    public List<Map<String,Object>> getCalendarList() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<CalendarDTO> calendarDTOList = calendarMapper.getCalendarList();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        calendarDTOList =
                calendarDTOList.stream()
                        .filter(dto -> Objects.equals(dto.getGroupId(), currentUser.getGroupId()))
                        .toList();

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
            map.put("color", colorMappingService.assignColor(calendarDTO.getUserId()));
            map.put("allDay", false);
            result.add(map);
        }

        return result;
    }

    @Override
    public List<CalendarDTO> getTodayCalendarList() {
        List<CalendarDTO> calendarDTOList = calendarMapper.getCalendarList();


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        calendarDTOList
                = calendarDTOList.stream()
                .filter(calendar -> Objects.equals(calendar.getGroupId(), currentUser.getGroupId()))
                .filter(dto -> dto.getStartDate().isBefore(LocalDateTime.now()))
                .filter(dto -> dto.getEndDate().isAfter(LocalDateTime.now()))
                .toList();

        return calendarDTOList;
    }

    @Override
    public int writeCalendar(String title, String content, LocalDateTime startDate, LocalDateTime endDate) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        log.info("currentUser: {}", currentUser);
        log.info("title: {}", title);
        log.info("content: {}", content);
        log.info("startDate: {}", startDate);
        log.info("endDate: {}", endDate);
        log.info("일정등록 서비스 진입");
        String userId = currentUser.getUserId();

        CalendarVO calendarVO = CalendarVO.builder()
                .calendarId(UUID.randomUUID().toString())
                .title(title)
                .content(content)
                .groupId(currentUser.getGroupId())
                .userId(userId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        log.info("calendarVO: {}", calendarVO);
        int result = calendarMapper.write(calendarVO);
        log.info("result: {}", result);

        List<UserVO> userEmailList = boardWriteMapper.getuserEmailList();
        userEmailList =
                userEmailList.stream()
                        .filter(vo -> vo.getRole().equals("ADMIN"))
                        .toList();

        String to;
        if (result > 0) {
            for (UserVO email : userEmailList) {
                to = email.getEmail();
                PostDTO postDTO = PostDTO.builder()
                        .title(title)
                        .content(content)
                        .createdAt(LocalDateTime.now())
                        .author(userId).build();
                mailService.sendPostNotification(to,postDTO);
            }
        }

        return result;
    }

    @Override
    public int deleteCalendar(String calendarId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        String userId = currentUser.getUserId();
        int result = calendarMapper.delete(calendarId,userId);
        return result;
    }

    @Override
    public int updateCalendar(String calendarId, int status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        int result = calendarMapper.update(calendarId, status,currentUser.getUserId());
        return result;
    }


}
