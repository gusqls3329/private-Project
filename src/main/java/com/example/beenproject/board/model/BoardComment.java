package com.example.beenproject.board.model;

import com.example.beenproject.eneities.enums.BoardCommentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardComment {
    private Long icomment;
    private Integer iuser;
    private String nick;
    private String userPic;
    private String comment;
    private LocalDateTime createdAt;
    private BoardCommentStatus commentStatus;
}
