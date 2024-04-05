package com.example.beenproject.eneities.enums;

import lombok.Getter;

@Getter
/*
ACTIVE : 활성화
DELETED : 유저 삭제
COMPULSION : 강제 삭제(신고로 인한 삭제)
 */
public enum BoardStatus {
    ACTIVE(1),
    DELETED(-1),
    COMPULSION(-2);

    private int status;

    BoardStatus(int status) {
        this.status = status;
    }
}
