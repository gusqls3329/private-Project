package com.example.beenproject.user.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String uid;
    private String upw;
    private String nick;
    private String email;
    private int isEmail;
    private String pic;
}
