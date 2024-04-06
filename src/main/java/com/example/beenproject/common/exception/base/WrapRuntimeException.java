package com.example.beenproject.common.exception.base;


import com.example.beenproject.common.exception.ErrorCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class WrapRuntimeException extends RuntimeException{
    private ErrorCode errorCode;
    public WrapRuntimeException(String message) {
        super(message);
        this.errorCode = Arrays.stream(ErrorCode.values()).filter(e -> e.getMessage().equals(message)).findFirst()
                .orElse(ErrorCode.SERVER_ERR_MESSAGE);
    }

    public WrapRuntimeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
