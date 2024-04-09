package com.example.beenproject.user;

import com.example.beenproject.common.ResVo;
import com.example.beenproject.user.model.SignUpDto;
import com.example.beenproject.user.model.SinginDto;
import com.example.beenproject.user.model.SinginVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입"
    +"사진만 비 필수")
    public ResVo postSignup(SignUpDto dto){
        return new ResVo(service.postSignup(dto));
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "아이디 4글자이상 15글자 이하")
    public SinginVo postSignin(HttpServletResponse http, SinginDto dto){
        return service.postSignin(http, dto);
    }

    @PostMapping("/check")
    @Operation(summary = "이메일 확인", description = "이메일 중복확인")
    public ResVo checkEmail(String email){
        return new ResVo(service.checkEmail(email));
    }

}
