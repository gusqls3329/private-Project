package com.example.beenproject.common.exception;


import com.example.beenproject.common.exception.base.BadInformationException;

public class BadAuthorizationException extends BadInformationException {
    public BadAuthorizationException(String message) {
        super(message);
    }

    public BadAuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
