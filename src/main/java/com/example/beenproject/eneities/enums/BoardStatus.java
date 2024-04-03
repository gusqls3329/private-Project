package com.example.beenproject.eneities.enums;

import lombok.Getter;

@Getter
public enum BoardStatus {
    ACTIVE(1),
    DELETED(-1),
    HIDDEN(-2);

    private int status;

    BoardStatus(int status) {
        this.status = status;
    }
}
