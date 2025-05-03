package com.teamflow.Planus.domain.user.api.mapper;

import com.teamflow.Planus.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BoardWriteMapper {
    int write(String title, String content, Long boardId, String userId, LocalDateTime now);
    List<UserVO> getuserEmailList();
}
