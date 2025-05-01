package com.teamflow.Planus.cache;

import com.teamflow.Planus.vo.UserVO;

public class LoginCache {
    private static final LoginCache instance = new LoginCache();
    private UserVO userVo;

    private LoginCache() {
    }

    public static LoginCache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("List is not initialized. Call getInstance(UserVO) first.");
        }
        return instance;
    }

    public UserVO getLoginStatus() {
        return userVo;
    }


    public void setLoginStatus(UserVO userVO) {
        this.userVo = userVO;
    }
}
