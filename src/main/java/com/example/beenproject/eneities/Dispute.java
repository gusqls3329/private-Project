package com.example.beenproject.eneities;

import com.example.beenproject.eneities.enums.DisputeStatus;
import com.example.beenproject.eneities.time.CreatedAt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dispute extends CreatedAt {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long idispute;

    @ManyToOne
    @JoinColumn(name = "iboard", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private User ireported;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private User ireporter;

    @Column(columnDefinition = "VARCHAR(500)")
    private String details;

    @Enumerated
    private DisputeStatus status;
}
