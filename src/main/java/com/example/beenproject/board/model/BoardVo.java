package com.example.beenproject.board.model;

import com.example.beenproject.eneities.BoardComment;
import com.example.beenproject.eneities.BoardPic;
import com.example.beenproject.eneities.enums.BoardStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BoardVo {
    private String nick;

    private String userPic;

    private Long isLiked;

    private Long iboard;

    private String title;

    private Long view;

    private BoardStatus status;

    private LocalDateTime createAt;

    private List<BoardPic> pics;

    private List<BoardComment> comments;

}
