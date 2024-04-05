package com.example.beenproject.eneities;

import com.example.beenproject.eneities.time.BaseAt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BoardComment extends BaseAt {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long icomment;

    @ManyToOne
    @JoinColumn(name = "iboard", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private User user;

    @Column(columnDefinition = "VARCHAR(200)")
    private String contents;


}
