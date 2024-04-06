package com.example.beenproject.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ILLEGAL_EX_MESSAGE(ErrorStatus.ILLEGAL_EX_MESSAGE.getCode(), ErrorMessage.ILLEGAL_EX_MESSAGE),

    CAN_NOT_BLANK_EX_MESSAGE(ErrorStatus.CAN_NOT_BLANK_EX_MESSAGE.getCode(), ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE),
    ILLEGAL_PROMISE_EX_MESSAGE(ErrorStatus.ILLEGAL_PROMISE_EX_MESSAGE.getCode(), ErrorMessage.ILLEGAL_PROMISE_EX_MESSAGE),
    NO_SUCH_USER_EX_MESSAGE(ErrorStatus.NO_SUCH_USER_EX_MESSAGE.getCode(), ErrorMessage.NO_SUCH_USER_EX_MESSAGE),

    SERVER_ERR_MESSAGE(ErrorStatus.SERVER_ERR_MESSAGE.getCode(), ErrorMessage.SERVER_ERR_MESSAGE);
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
