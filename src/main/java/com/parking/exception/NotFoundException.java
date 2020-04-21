package com.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public NotFoundException(String msg) {
        super(msg);
    }
    public NotFoundException(Throwable cause) {
        super(cause);
    }
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}