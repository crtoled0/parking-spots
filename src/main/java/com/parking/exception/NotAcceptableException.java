package com.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public NotAcceptableException(String msg) {
        super(msg);
    }
    public NotAcceptableException(Throwable cause) {
        super(cause);
    }
    public NotAcceptableException(String message, Throwable cause) {
        super(message, cause);
    }
}