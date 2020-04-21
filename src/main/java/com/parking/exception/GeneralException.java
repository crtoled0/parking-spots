package com.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public class GeneralException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public GeneralException(String msg) {        
        super(msg);
    }
    public GeneralException(Throwable cause) {
        super(cause);
    }
    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }
}