package com.teamflow.Planus.domain.auth.signup.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCheckMapper {

    boolean existsByUserId(String userId);
    boolean existsByGroupId(Long groupId);
}
