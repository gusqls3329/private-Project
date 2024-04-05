package com.example.beenproject.eneities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long iadmin;

    @Column(columnDefinition = "VARCHAR(10)")
    private String nick;
}
