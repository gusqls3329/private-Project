package com.example.beenproject.eneities;

import com.example.beenproject.eneities.enums.ProvideType;
import com.example.beenproject.eneities.enums.UserStatus;
import com.example.beenproject.eneities.time.BaseAt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseAt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long iuser;

    @Column(columnDefinition = "VARCHAR(10)")
    private String nick;

    @Column(columnDefinition = "VARCHAR(20)")
    private String uid;

    @Column(columnDefinition = "VARCHAR(2000)")
    private String upw;

    @Column(columnDefinition = "VARCHAR(30)")
    private String email;

    @Enumerated(EnumType.STRING)
    private ProvideType provideType;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String pic;

    @Column(length = 2100, name = "firebase_token")
    private String firebaseToken;
}
