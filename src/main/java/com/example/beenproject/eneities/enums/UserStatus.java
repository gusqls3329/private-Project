package com.example.beenproject.eneities.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE(1),
    DELETED(-1);

    private int status;

    UserStatus(int status) {
        this.status = status;
    }

}
