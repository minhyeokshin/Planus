package com.teamflow.Planus.domain.auth.login.service;

import com.teamflow.Planus.cache.LoginCache;
import com.teamflow.Planus.domain.auth.login.repository.LoginMapper;
import com.teamflow.Planus.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final LoginMapper loginMapper;

    @Override
    public int login(String userId, String password) {
        int result = 0;
        UserVO userVO = loginMapper.login(userId);
        if (userVO != null && userVO.getPassword().equals(password)) {
            LoginCache.getInstance().setLoginStatus(userVO);
            result = 1;
        }
        return result;
    }
}
