package com.teamflow.Planus.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
        private String userId;
        private String username;
        private String email;
        private String phone;
        private String loginId;
        private String password;
        private String role;

    }

