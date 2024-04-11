package com.example.beenproject.user.model;

import com.example.beenproject.common.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PutUserDto {
    @Length(max = 30, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @Schema(defaultValue = "bobo@naver.com")
    private String email;

    @Schema(defaultValue = "1")
    private int isEmail;

    @Length(min = 8, max = 20, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    @Schema(defaultValue = "bobo1234")
    private String upw;

    @JsonIgnore
    private MultipartFile pic;
    @JsonIgnore
    private String chPic;
}
