package com.example.beenproject.board;

import com.example.beenproject.board.model.*;
import com.example.beenproject.common.Const;
import com.example.beenproject.common.security.AuthenticationFacade;
import com.example.beenproject.eneities.Board;
import com.example.beenproject.eneities.BoardPic;
import com.example.beenproject.eneities.User;
import com.example.beenproject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository repository;
    private final AuthenticationFacade authenticationFacade;
    private final BoardPicRepository picRepository;

    public long postBoard(InsBoardDto dto){
        Board board = new Board();
        board.setContents(dto.getContents());
        board.setView(0L);
        board.setContents(dto.getContents());
        board.setTitle(dto.getContents());
        User user =  userRepository.findByIuser(authenticationFacade.getLoginUserPk());
        board.setUser(user);
        repository.save(board);
        return Const.SUCCESS;
    }
    public BoardListVo getBoardList(BoardListDto dto){
        BoardListVo vo = new BoardListVo();
        return vo;
    }

    public BoardVo getBoard(Long iboard) {
        Board board = repository.findByIboard(iboard);
        User user = userRepository.findByIuser(board.getUser().getIuser());
        List<BoardPic> boardPic = picRepository.findByBoard(board);
        List<BoardPics> picss
        for(int i = 0; i<boardPic.size(); i++){
            pics.setIboardPic(boardPic.get(i).getIboardPic());
            pics.setStoredPic(boardPic.get(i).getStoredPic());
        }

        BoardVo.builder()
                .nick(user.getNick())
                .userPic(user.getPic())
                .title(board.getTitle())
                .comments(null) //수정 필수~~~~~~~~~~~~~~
                .createAt(board.getCreatedAt())
                .view(board.getView())
                .pics(picss)
                .build();
        return vo;
    }

    public long putBoard(PutBoardDto dto){
        return 0;
    }

    public long delUserBoard( Long iboard){
        return 0;
    }

    public long toggleLike(Long iboard){
        return 0;
    }
}
