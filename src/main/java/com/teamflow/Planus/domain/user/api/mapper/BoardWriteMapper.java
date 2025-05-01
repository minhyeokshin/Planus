package com.teamflow.Planus.domain.user.api.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardWriteMapper {
    int write(String title, String content, Long boardId,String userId);
}
