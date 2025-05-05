package com.teamflow.Planus.domain.auth.signup.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupCheckMapper {
    boolean existsByGroupName(String groupName);
}
