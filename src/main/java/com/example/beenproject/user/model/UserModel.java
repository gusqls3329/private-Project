package com.example.beenproject.user.model;

import com.example.beenproject.eneities.enums.ProvideType;
import com.example.beenproject.eneities.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    private Long iuser;
    private String uid;
    private String upw;
    private String email;
    private UserStatus status;
    private String nick;
    private String provideType;
    private String pic;
    private String firebaseToken;
    private String createdAt;
    private String updatedAt;

}
