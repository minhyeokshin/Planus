package com.teamflow.Planus.domain.auth.signup.service;

public interface GroupCheckService {
    boolean existsByGroupName(String groupName);
}
