package com.example.beenproject.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PutBoardDto {
    private String title;
    private Long iboard;
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
