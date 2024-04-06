package com.example.beenproject.user;

import com.example.beenproject.common.ResVo;
import com.example.beenproject.user.model.SignUpDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("signup")
    @Operation(summary = "회원가입", description = "회원가입"
    +"사진만 비 필수")
    public ResVo postSignup(SignUpDto dto){
        return new ResVo(service.postSignup(dto));
    }

}
