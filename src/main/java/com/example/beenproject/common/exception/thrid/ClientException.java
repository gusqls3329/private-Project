package com.example.beenproject.common.exception.thrid;

import com.example.beenproject.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public class ClientException extends RuntimeException{

    private ErrorCode errorCode;
    private String reason;

    public ClientException(String message) {
        super(message);
        this.errorCode = Arrays.stream(ErrorCode.values()).filter(e -> e.getMessage().equals(message)).findFirst()
                .orElse(ErrorCode.SERVER_ERR_MESSAGE);
    }

    public ClientException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }


}
