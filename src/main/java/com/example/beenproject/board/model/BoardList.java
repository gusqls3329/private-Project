package com.example.beenproject.board.model;

import com.example.beenproject.eneities.enums.BoardStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardList {
    private String nick;

    private String userPic;

    private Long boardLikeCnt;

    private Long isLiked;

    private Long iboard;

    private String title;

    private Long view;

    private BoardStatus status;

    private LocalDateTime createAt;
}
