package com.teamflow.Planus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String username;
    private String email;
    private Long groupId;
    private String phone;
    private String loginId;
    private String password;
    private String role;
    private String userPhone1;
    private String userPhone2;
    private String userPhone3;
}
