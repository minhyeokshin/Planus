package com.teamflow.Planus.domain.auth.signup.service;


import com.teamflow.Planus.dto.GroupDTO;
import com.teamflow.Planus.dto.UserDTO;

public interface SignupService {
    boolean signUp(UserDTO userDTO);

    boolean isUserIdExist(String userId);

    boolean groupSignUp(GroupDTO groupDTO);


}
