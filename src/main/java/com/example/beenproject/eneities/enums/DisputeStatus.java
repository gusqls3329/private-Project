package com.example.beenproject.eneities.enums;

import lombok.Getter;

@Getter
//refuse : 거절(삭제 안됨)
//COMPULSION : 강제 삭제됨
//progress : 진행중
public enum DisputeStatus {
    COMPULSION(-1),
    REFUSE(1),
    ACCEPT(2);

    private int status;

    DisputeStatus(int status) {
        this.status = status;
    }
}
