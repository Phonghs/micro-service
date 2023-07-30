package com.example.gateway.exception;

import com.example.common.proxy.exception.BaseWebException;
import org.springframework.http.HttpStatus;

public class UnAuthorizedRequestException extends BaseWebException {


    public UnAuthorizedRequestException(String message, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, message, cause);
    }

    public UnAuthorizedRequestException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnAuthorizedRequestException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
