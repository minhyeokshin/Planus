package com.teamflow.Planus.domain.user.dashboard.Mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DashBoardMapper {
    List<String> getUserNameList();
}
