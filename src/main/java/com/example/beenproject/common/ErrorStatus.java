package com.example.beenproject.common;

import lombok.Getter;

@Getter
public enum ErrorStatus {
    ILLEGAL_EX_MESSAGE(499),


    SERVER_ERR_MESSAGE(500);
    private int code;

    ErrorStatus(int code) {
        this.code = code;
    }
}
