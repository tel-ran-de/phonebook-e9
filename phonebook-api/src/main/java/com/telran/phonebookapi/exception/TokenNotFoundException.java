package com.telran.phonebookapi.exception;

import org.springframework.http.HttpStatus;

public class TokenNotFoundException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public TokenNotFoundException() {
        super("Please sign up.");
    }
}
