package com.example.beenproject.eneities.ids;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BoardLikeIds implements Serializable {
    private Long iuser;
    private Long iboard;

}
