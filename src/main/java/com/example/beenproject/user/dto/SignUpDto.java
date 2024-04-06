package com.example.beenproject.user.dto;

import com.example.beenproject.common.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SignUpDto {
    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Pattern(regexp =  "^\\w{4,15}$", message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @Length(min = 4, max = 15, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    private String uid;

    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Length(min = 8, max = 20, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    private String upw;

    private String nick;
    private String email;
    private int isEmail;
    private String pic;
}
