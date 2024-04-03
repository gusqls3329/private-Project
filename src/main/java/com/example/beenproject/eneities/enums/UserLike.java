package com.example.beenproject.eneities.enums;

import com.example.beenproject.eneities.User;
import com.example.beenproject.eneities.time.CreatedAt;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserLike extends CreatedAt {
    @Id
    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private User user;

}
