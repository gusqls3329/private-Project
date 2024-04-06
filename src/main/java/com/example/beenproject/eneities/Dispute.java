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

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "ireported_user")
    private User reportedUser;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "ireporter")
    private User reporter;

    @Column(columnDefinition = "VARCHAR(500)")
    private String details;

    @Enumerated
    private DisputeStatus status;
}
