package com.teamflow.Planus.domain.auth.signup.service;


import com.teamflow.Planus.domain.auth.signup.mapper.GroupCheckMapper;
import com.teamflow.Planus.domain.auth.signup.mapper.SignupMapper;
import com.teamflow.Planus.dto.GroupDTO;
import com.teamflow.Planus.dto.PostDTO;
import com.teamflow.Planus.dto.UserDTO;
import com.teamflow.Planus.util.MailService;
import com.teamflow.Planus.util.SnowflakeIdGenerator;
import com.teamflow.Planus.vo.GroupVO;
import com.teamflow.Planus.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
//import org.apache.commons.codec.digest.DigestUtils;


@Service
@RequiredArgsConstructor
@Log4j2
public class SignupServiceImpl implements SignupService {
    private final SignupMapper signupMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final GroupCheckMapper groupCheckMapper;

    @Override
    public boolean signUp(UserDTO userDTO) {
        // 아이디 중복 검사
        if (signupMapper.existCheckByUserId(userDTO.getLoginId()) > 0) {
            return false;
        }
        // Use only the part before any comma as the raw password
        String rawPwd = userDTO.getPassword().split(",")[0].trim();
        log.info("해시 변환전(rawPwd) :{}", rawPwd);
        String bcrypt = passwordEncoder.encode(rawPwd);
        userDTO.setPassword(bcrypt);
        log.info("가입 서비스 userDTO (bcrypt 저장 후): {}", userDTO.toString());
        String Phone = userDTO.getUserPhone1()+"-"+userDTO.getUserPhone2()+"-"+userDTO.getUserPhone3();

        String roll;
        if(userDTO.getRole() != null &&
        (userDTO.getRole().equals("ADMIN") || userDTO.getRole().equals("admin"))){
            roll = "ADMIN";
        }else{
            roll = "USER";
        }


        // 서버에서 세팅해야 하는 필드들
        UserVO userVO = UserVO.builder()
                .userId(UUID.randomUUID().toString())
                .username(userDTO.getUsername())
                .groupId(userDTO.getGroupId())
                .email(userDTO.getEmail())
                .phone(Phone)
                .role(roll)
                .loginId(userDTO.getLoginId())
                .password(userDTO.getPassword())
                .build();

        PostDTO postDTO = PostDTO.builder()
                .title(userDTO.getUsername())
                .build();

        boolean result = false;


        if(signupMapper.insertUser(userVO) > 0){
            result = true;
            mailService.sendSignUpNotify(userDTO.getEmail(), postDTO);
        }

        return result;
    }

    @Override
    public boolean isUserIdExist(String userId) {
        return signupMapper.existCheckByUserId(userId) > 0;
    }

    @Override
    public boolean groupSignUp(GroupDTO groupDTO) {

        // 트위터 Snowflake 알고리즘 활용
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator();
        Long groupId = generator.nextId();
        log.info("groupId: {}", groupId);

        GroupVO groupVO = GroupVO.builder()
                .groupId(groupId)
                .groupName(groupDTO.getGroupName())
                .groupEmail(groupDTO.getGroupEmail())
                .build();



        if(groupCheckMapper.existsByGroupName(groupDTO.getGroupName())){
            return false;
        }

        String rawPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 12);

        if (signupMapper.insertGroup(groupVO) > 0 ){

            UserDTO userDTO = UserDTO.builder()
                    .username(groupDTO.getGroupName())
                    .groupId(groupId)
                    .email(groupDTO.getGroupEmail())
                    .role("ADMIN")
                    .loginId(UUID.randomUUID().toString().replace("-", "").substring(0, 10))
                    .password(rawPassword)
                    .build();

            signUp(userDTO);

            String content = String.valueOf(groupId)
                    + "<br><br>관리자 ID : " + userDTO.getLoginId()
                    + "<br><br>관리자 PW : " + rawPassword;

            PostDTO postDTO = PostDTO.builder()
                    .title(groupDTO.getGroupName())
                    .content(content)
                    .build();

            mailService.sendGroupNotify(groupDTO.getGroupEmail(), postDTO);

            return true;
        }


        return false;
    }


}
