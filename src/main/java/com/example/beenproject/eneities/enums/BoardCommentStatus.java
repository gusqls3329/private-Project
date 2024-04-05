package com.example.beenproject.eneities.enums;

import lombok.Getter;

@Getter
public enum BoardCommentStatus {
    ACTIVE(1),
    DELETED(-1);

    private int status;

    BoardCommentStatus(int status) {
        this.status = status;
    }
}
