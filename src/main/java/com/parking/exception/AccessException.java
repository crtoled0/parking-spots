package com.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class AccessException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public AccessException(String msg) {
        super(msg);
    }
    public AccessException(Throwable cause) {
        super(cause);
    }
    public AccessException(String message, Throwable cause) {
        super(message, cause);        
    }
}