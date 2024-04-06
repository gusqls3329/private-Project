package com.example.beenproject.common.exception;


import com.example.beenproject.common.exception.base.BadInformationException;

public class BadWordException extends BadInformationException {
    public BadWordException(String message) {
        super(message);
    }

    public BadWordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
