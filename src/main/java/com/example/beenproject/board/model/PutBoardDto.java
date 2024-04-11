package com.example.beenproject.board.model;

import com.example.beenproject.common.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PutBoardDto {
    @Length(min = 2, max = 50, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    private String title;

    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    @Length(min = 1,  message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    private Long iboard;

    @Length(min = 2, max = 5000, message = ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE)
    private String contents;

    @JsonIgnore
    private List<MultipartFile> pics;
    @JsonIgnore
    private MultipartFile mainPic;
    @JsonIgnore
    private String ChMainPic;
    @JsonIgnore
    private List<String> ChPics;
}
