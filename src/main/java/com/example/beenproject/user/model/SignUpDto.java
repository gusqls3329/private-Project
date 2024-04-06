package com.example.beenproject.user.model;

import com.example.beenproject.common.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int iuser;
    @JsonIgnore
    private String chPic;
    @JsonIgnore
    private String providerType;

    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Pattern(regexp =  "^\\w{4,15}$", message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @Length(min = 4, max = 15, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    private String uid;

    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Length(min = 8, max = 20, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    private String upw;

    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Pattern(regexp = "^\\S{0,20}$", message = ErrorMessage.NO_SUCH_USER_EX_MESSAGE)
    @Length(max = 20, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    private String nick;

    @Length(max = 30, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    private String email;

    @NotNull(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Range(min = 2, max = 2, message = ErrorMessage.BAD_EMAIL_EX_MESSAGE)
    private int isEmail;

    private MultipartFile pic;
}
