package com.example.common.proxy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseWebException extends RuntimeException{

    private final HttpStatus httpStatus;

    public BaseWebException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public BaseWebException(HttpStatus httpStatus, String message) {
        this(httpStatus,message,null);
    }

    public BaseWebException(HttpStatus httpStatus) {
        super();
        this.httpStatus = httpStatus;
    }

}
