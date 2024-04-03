package com.example.beenproject.eneities.enums;

import com.example.beenproject.eneities.Board;
import com.example.beenproject.eneities.User;
import com.example.beenproject.eneities.ids.BoardLikeIds;
import com.example.beenproject.eneities.time.CreatedAt;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;
@Getter
@Setter
@Entity
public class BoardLike extends CreatedAt {
    @EmbeddedId
    private BoardLikeIds boardLikeIds;

    @ManyToOne
    @MapsId("iuser")
    @JoinColumn(name = "iuser", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("iboard")
    @JoinColumn(name = "iboard", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Board board;

}
