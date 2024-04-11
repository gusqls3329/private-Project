package com.example.beenproject.board;

import com.example.beenproject.eneities.Board;
import com.example.beenproject.eneities.BoardPic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardPicRepository extends JpaRepository<BoardPic, Long> {
    List<BoardPic> findByBoard(Board board);
}
