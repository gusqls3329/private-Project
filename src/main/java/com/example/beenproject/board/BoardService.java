package com.example.beenproject.board;

import com.example.beenproject.board.model.*;
import com.example.beenproject.common.ResVo;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    public long putBoard(PutBoardDto dto){
        return 0;
    }

    public long delUserBoard( int iboard){
        return 0;
    }

    public long toggleLike(int iboard){

    }
}
