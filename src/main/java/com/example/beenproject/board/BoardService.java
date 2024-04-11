package com.example.beenproject.board;

import com.example.beenproject.board.model.BoardListDto;
import com.example.beenproject.board.model.BoardListVo;
import com.example.beenproject.board.model.BoradVo;
import com.example.beenproject.board.model.InsBoardDto;
import com.example.beenproject.common.ResVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    public long postBoard(InsBoardDto dto){
        return 0;
    }
    public BoardListVo getBoardList(BoardListDto dto){
        BoardListVo vo = new BoardListVo();
        return vo;
    }

    public BoradVo getBoard(int iboard) {
        BoradVo vo = new BoradVo();
        return vo;
    }
}
