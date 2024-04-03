package com.example.beenproject.eneities;

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

    @Column(columnDefinition = "VARCHAR(30)")
    private String title;


}
