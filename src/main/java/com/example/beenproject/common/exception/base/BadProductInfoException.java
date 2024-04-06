package com.example.beenproject.common.exception.base;


import com.example.beenproject.common.exception.ErrorCode;

public class BadProductInfoException extends BadInformationException{
    public BadProductInfoException(String message) {
        super(message);
    }

    public BadProductInfoException(ErrorCode errorCode) {
        super(errorCode);
    }
}
