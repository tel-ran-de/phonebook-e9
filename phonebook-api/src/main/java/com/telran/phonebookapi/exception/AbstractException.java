package com.telran.phonebookapi.exception;

import org.springframework.http.HttpStatus;

public class AbstractException extends RuntimeException {

    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public AbstractException(String message) {
        super(message);
    }
}
