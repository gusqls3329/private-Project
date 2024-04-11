package com.example.beenproject.board.model;

import lombok.Data;

import java.util.List;

@Data
public class BoardListVo {
    private Long totalBoardCount;

    private List<BoardList> board;
}
