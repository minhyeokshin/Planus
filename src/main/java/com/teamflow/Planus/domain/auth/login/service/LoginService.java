package com.teamflow.Planus.domain.auth.login.service;

public interface LoginService {
    int passwordChange(String password,String newPassword,String newPasswordCheck);
}
