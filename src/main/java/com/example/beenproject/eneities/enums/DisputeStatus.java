package com.example.beenproject.eneities.enums;

import lombok.Getter;

@Getter
//refuse : 거절
//accept : 수락
//progress : 진행중
public enum DisputeStatus {
    REFUSE(-1),
    PROGRESS(1),
    ACCEPT(2);

    private int status;

    DisputeStatus(int status) {
        this.status = status;
    }
}
