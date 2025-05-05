package com.teamflow.Planus.domain.auth.signup.service;

import com.teamflow.Planus.domain.auth.signup.mapper.GroupCheckMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupCheckServiceImpl implements GroupCheckService {

    private final GroupCheckMapper groupCheckMapper;

    @Override
    public boolean existsByGroupName(String groupName) {
        return groupCheckMapper.existsByGroupName(groupName);
    }
}
