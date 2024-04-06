package com.example.beenproject.eneities.enums;

import lombok.Getter;

@Getter
/*
ACTIVE : 활성화
HIDDEN : 비활성화(유저가 자신만 보기)
DELETED : 유저 삭제
COMPULSION : 강제 삭제(신고로 인한 삭제)
 */
public enum BoardStatus {
    ACTIVE(1),
    HIDDEN(-1),
    DELETED(-2),
    COMPULSION(-3);

    private int status;

    BoardStatus(int status) {
        this.status = status;
    }
}
