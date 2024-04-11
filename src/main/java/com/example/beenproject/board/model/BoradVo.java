package com.example.beenproject.board.model;

import com.example.beenproject.eneities.enums.BoardStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoradVo {
    private String nick;

    private String userPic;

    private Long isLiked;

    private Long iboard;

    private String title;

    private Long view;

    private BoardStatus status;

    private LocalDateTime createAt;

    private List<BoardPics> pics;

    private List<BoardComment> comments;

}
