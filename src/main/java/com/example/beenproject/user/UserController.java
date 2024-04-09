package com.example.beenproject.user;

import com.example.beenproject.common.ResVo;
import com.example.beenproject.user.model.SignUpDto;
import com.example.beenproject.user.model.SinginDto;
import com.example.beenproject.user.model.SinginVo;
import com.example.beenproject.user.model.UserFirebaseTokenPatchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입"
    +"사진만 비 필수"+ "아이디 4글자이상 15글자 이하"+"비밀번호 8이상 15자이하")
    public ResVo postSignup(SignUpDto dto){
        return new ResVo(service.postSignup(dto));
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "아이디 4글자이상 15글자 이하"+"비밀번호 8이상 15자이하")
    public SinginVo postSignin(HttpServletResponse http, SinginDto dto){
        return service.postSignin(http, dto);
    }


    @PostMapping("/check")
    @Operation(summary = "이메일 확인", description = "이메일 중복확인")
    public ResVo checkEmail(String email){
        return new ResVo(service.checkEmail(email));
    }



    @PostMapping("/signout")
    public ResVo getSignOut(HttpServletResponse res){
        return new ResVo(service.getSignOut(res));
    }

    @GetMapping("/refrech-token")
    public SinginVo getRefrechToken(HttpServletRequest req){
        return service.getRefrechToken(req);
    }
    @Operation(summary = "fireBaseToken 등록", description = "발급받은 해당 유저의 브라우저에 발급된 " +
            "fireBaseToken 을 로그인한 유저에 등록")
    @Parameters(value = {
            @Parameter(name = "firebaseToken", description = "토큰값")
    })
    @PatchMapping("/firebase-token")
    public ResVo patchUserFirebaseToken(@RequestBody UserFirebaseTokenPatchDto dto) {
        return new ResVo(service.patchUserFirebaseToken(dto));
    }

}
