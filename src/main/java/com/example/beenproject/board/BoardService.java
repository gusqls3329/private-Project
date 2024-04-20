package com.example.beenproject.board;

import com.example.beenproject.board.model.*;
import com.example.beenproject.board.model.comment.CommentRepository;
import com.example.beenproject.common.ClientException;
import com.example.beenproject.common.Const;
import com.example.beenproject.common.ErrorCode;
import com.example.beenproject.common.security.AuthenticationFacade;
import com.example.beenproject.eneities.Board;
import com.example.beenproject.eneities.BoardComment;
import com.example.beenproject.eneities.BoardPic;
import com.example.beenproject.eneities.User;
import com.example.beenproject.eneities.enums.BoardStatus;
import com.example.beenproject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository repository;
    private final AuthenticationFacade authenticationFacade;
    private final BoardPicRepository picRepository;
    private final CommentRepository commentRepository;


    public long postBoard(InsBoardDto dto){
        Board board = new Board();
        board.setContents(dto.getContents());
        board.setView(0L);
        board.setTitle(dto.getContents());
        User user =  userRepository.findByIuser(authenticationFacade.getLoginUserPk());
        board.setUser(user);
        repository.save(board);

        BoardPic pic = new BoardPic();
        pic.setBoard(board);
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
        List<BoardComment> comments = commentRepository.findByBoard(board);
        return BoardVo.builder()
                .nick(user.getNick())
                .userPic(user.getPic())
                .title(board.getTitle())
                .comments(comments)
                .createAt(board.getCreatedAt())
                .view(board.getView())
                .pics(boardPic)
                .build();
    }

    public long putBoard(PutBoardDto dto){
        if(dto.getTitle() == null && dto.getContents() == null &&
                (dto.getPics()==null || !dto.getPics().isEmpty()) || (dto.getMainPic()==null || !dto.getMainPic().isEmpty())){
            throw new ClientException(ErrorCode.ILLEGAL_EX_MESSAGE);
        }

        Board board = repository.findByIboard(dto.getIboard());
        List<BoardPic> pics = picRepository.findByBoard(board);
        if(!board.getUser().getIuser().equals(authenticationFacade.getLoginUserPk())){
            throw new ClientException(ErrorCode.ILLEGAL_EX_MESSAGE);
        }

        if(board.getStatus() != BoardStatus.ACTIVE){
            throw new ClientException(ErrorCode.ILLEGAL_EX_MESSAGE);
        }

        //
        if(dto.getTitle() != null){
        board.setTitle(dto.getTitle());
        }
        if(dto.getContents()!=null){
        board.setContents(dto.getContents());}

        if(dto.getPics()!=null || !dto.getPics().isEmpty()){

        }

        return 0;
    }

    public long delUserBoard(Long iboard){
        return 0;
    }

    public long toggleLike(Long iboard){
        return 0;
    }
}
