package com.teamflow.Planus.domain.auth.signup.service;

public interface UserCheckService {
    boolean existsByUserId(String userId);
    boolean excistsByGroupId(Long groupId);
}
