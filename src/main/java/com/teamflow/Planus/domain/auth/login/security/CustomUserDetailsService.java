package com.teamflow.Planus.domain.auth.login.security;


import com.teamflow.Planus.domain.auth.login.repository.LoginMapper;
import com.teamflow.Planus.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginMapper loginMapper;

    @Override
    public UserDetails loadUserByUsername(String id) {
        log.info("🟢 로그인 시도: {}", id);

            UserVO user = loginMapper.login(id);

            if (user == null) {
                log.warn("❌ 사용자 정보 없음: {}", id);
                throw new UsernameNotFoundException("사용자 없음: " + id);
            }

            log.info("🔍 user.getClientId() = {}", user.getUserId());
            CustomUserDetails customUser = new CustomUserDetails();
            customUser.setId(user.getLoginId());
            customUser.setRole("ROLE_"+user.getRole());
            customUser.setPassword(user.getPassword());
            customUser.setUserId(user.getUserId());
            customUser.setName(user.getUsername());
            customUser.setGroupId(user.getGroupId());
            return customUser;
    }
}
