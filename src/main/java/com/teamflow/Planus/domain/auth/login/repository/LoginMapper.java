package com.teamflow.Planus.domain.auth.login.repository;

import com.teamflow.Planus.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

    UserVO login(String loginId);
}
