package com.teamflow.Planus.domain.auth.signup.service;


import com.teamflow.Planus.domain.auth.signup.mapper.UserCheckMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCheckServiceImpl implements UserCheckService {

    private final UserCheckMapper userCheckMapper;

    @Override
    public boolean existsByUserId(String userId) {
        return userCheckMapper.existsByUserId(userId);
    }

    @Override
    public boolean excistsByGroupId(Long groupId) {
        return userCheckMapper.existsByGroupId(groupId);
    }
}
