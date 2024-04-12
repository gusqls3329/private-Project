package com.example.beenproject.board.model.comment;

import com.example.beenproject.eneities.Board;
import com.example.beenproject.eneities.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<BoardComment, Long> {
    List<BoardComment> findByBoard(Board board);

}
