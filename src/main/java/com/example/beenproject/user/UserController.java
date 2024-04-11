package com.example.beenproject.user;

import com.example.beenproject.common.ErrorMessage;
import com.example.beenproject.common.ResVo;
import com.example.beenproject.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "회원가입", description = "회원가입"
            +"사진만 비 필수"+ "아이디 4글자이상 15글자 이하"+"비밀번호 8이상 15자이하")
    public ResVo postSignup(@RequestPart(required = false) MultipartFile pic, @RequestPart @Validated SignUpDto dto){
        dto.setPic(pic);
        return new ResVo(service.postSignup(dto));
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "아이디 4글자이상 15글자 이하"+"비밀번호 8이상 15자이하")
    public SinginVo postSignin(HttpServletResponse http, @Validated @RequestBody SinginDto dto){
        return service.postSignin(http, dto);
    }


    @PostMapping("/check")
    @Operation(summary = "이메일 확인", description = "이메일 중복확인")
    @Validated
    public ResVo checkEmail(@Length(max = 30, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
                                @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
                                @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
                                @RequestParam String email){
        return new ResVo(service.checkEmail(email));
    }

    @GetMapping("/id")
    @Operation(summary = "아이디 찾기", description = "이메일을 통한 아이디 찾기")
    @Validated
    public FindUidVo getFindUid(@Length(max = 30, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
                                    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
                                    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
                                    @Schema(defaultValue = "bobo@naver.com")
                                    String email){
        return service.getFindUid(email);
    }

    @PostMapping("/pw")
    @Operation(summary = "비밀번호 변경", description = "이메일,아이디를 통한 비밀번호 변경")
    public ResVo getFindUpw(@Validated @RequestBody FindUpwDto dto){
        return new ResVo(service.getFindUpw(dto));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "수정", description = "사진 or 이메일만 수정가능하며 2개중 하나는 무조건 하나는 필수, 이메일은 중복확인 되어야함")
    public ResVo putUser(@RequestPart(required = false) MultipartFile pic, @RequestPart @Validated PutUserDto dto){
        dto.setPic(pic);
        return new ResVo(service.putUser(dto));
    }

    @PatchMapping
    @Operation(summary = "유저탈퇴", description = "유저탈퇴")
    public ResVo patchUser(@Validated @RequestBody DelUserDto dto){
        return new ResVo(service.patchUser(dto));
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
