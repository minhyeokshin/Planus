package com.teamflow.Planus.domain.auth.signup.mapper;

import com.teamflow.Planus.vo.GroupVO;
import com.teamflow.Planus.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignupMapper {
    int insertUser(UserVO userVO);
    int existCheckByUserId(String loginId);
    int insertGroup(GroupVO groupVO);
}
