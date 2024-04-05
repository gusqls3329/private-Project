package com.example.beenproject.eneities;

import com.example.beenproject.eneities.time.CreatedAt;
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
public class BoardPic extends CreatedAt {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long BoardPic;

    @ManyToOne
    @JoinColumn(name = "iboard", nullable = false)
    private Board board;

    @Column
    private String storedPic;



}
