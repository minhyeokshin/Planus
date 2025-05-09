package com.teamflow.Planus.domain.auth.signup.controller;


import com.teamflow.Planus.domain.auth.signup.service.SignupService;
import com.teamflow.Planus.dto.GroupDTO;
import com.teamflow.Planus.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Log4j2
public class SignupController {

    private final SignupService signupService;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UserDTO());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute UserDTO userDTO, RedirectAttributes rttr) {
        log.info("userDTO : {}", userDTO);

        if(signupService.isUserIdExist(userDTO.getUserId())) {
            rttr.addFlashAttribute("msg", "이미 사용 중인 아이디입니다.");
            rttr.addFlashAttribute("user", userDTO);
            return "redirect:/signup";
        }

        boolean result = signupService.signUp(userDTO);

        if (result) {
            rttr.addFlashAttribute("msg", "회원가입 성공!");
            log.info("----------------- : " + userDTO.getUserId());
            return "redirect:/login";
        } else {
            rttr.addFlashAttribute("msg", "회원가입 실패");
            rttr.addFlashAttribute("user", userDTO);
            return "redirect:/signup";
        }
    }

    @GetMapping("/groupsignup")
    public String groupSignup(Model model) {
        model.addAttribute("group", new GroupDTO());
        return "groupsignup";
    }

    @PostMapping("/groupsignup")
    public String processGroupSignup(@ModelAttribute GroupDTO groupDTO, RedirectAttributes rttr) {
        log.info("groupDTO : {}", groupDTO);
        boolean result = signupService.groupSignUp(groupDTO);

        if (result) {
            rttr.addFlashAttribute("msg", "그룹등록 성공! 그룹번호 이메일 확인부탁드립니다!");
            log.info("----------------- : " + groupDTO.getGroupName());
            return "redirect:/login";
        } else {
            rttr.addFlashAttribute("msg", "그룹등록 실패");
            rttr.addFlashAttribute("user", groupDTO);
            return "redirect:/groupsignup";
        }
    }
}
