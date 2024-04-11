package com.example.beenproject.board;

import com.example.beenproject.eneities.Board;
import com.example.beenproject.eneities.BoardPic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByIboard(Long iboard);
}
