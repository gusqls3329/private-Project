package com.example.beenproject.common;

import lombok.Getter;

@Getter
public enum ErrorStatus {
    ILLEGAL_EX_MESSAGE(499),
    //
    CAN_NOT_BLANK_EX_MESSAGE(401),
    ILLEGAL_PROMISE_EX_MESSAGE(402),
    NO_SUCH_USER_EX_MESSAGE(403),
    BAD_EMAIL_EX_MESSAGE(404),
    ILLEGAL_UID_MESSAGE(405),
    ILLEGAL_UPW_MESSAGE(406),
    //
    SERVER_ERR_MESSAGE(500);
    private int code;

    ErrorStatus(int code) {
        this.code = code;
    }
}
