package com.example.beenproject.user.model;

import com.example.beenproject.common.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SignUpDto {
    @JsonIgnore
    private Long iuser;
    @JsonIgnore
    private String chPic;
    @JsonIgnore
    private String providerType;

    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Pattern(regexp =  "^\\w{4,15}$", message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @Length(min = 4, max = 15, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @Schema(defaultValue = "bobo")
    private String uid;

    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Length(min = 8, max = 20, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @Schema(defaultValue = "bobo1234")
    private String upw;

    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Pattern(regexp = "^\\S{0,20}$", message = ErrorMessage.NO_SUCH_USER_EX_MESSAGE)
    @Length(max = 20, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @Schema(defaultValue = "바오밥")
    private String nick;

    @Length(max = 30, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Schema(defaultValue = "bobo@naver.com")
    private String email;

    @NotNull(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Range(min = 1, max = 1, message = ErrorMessage.BAD_EMAIL_EX_MESSAGE)
    @Schema(defaultValue = "1")
    private int isEmail;

    @JsonIgnore
    private MultipartFile pic;
}
