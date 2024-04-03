package com.example.beenproject.eneities;

import com.example.beenproject.eneities.enums.BoardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long iboard;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private User user;

    @Column(columnDefinition = "VARCHAR(50)")
    private String title;

    @Column(columnDefinition = "VARCHAR(5000)")
    private String contents;

    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long view;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

}
