package com.example.beenproject.eneities;

import com.example.beenproject.eneities.enums.Reson;
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
public class CompulsionBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long iresolvedBoard;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private User user;

    @Enumerated
    private Reson reson;
}
