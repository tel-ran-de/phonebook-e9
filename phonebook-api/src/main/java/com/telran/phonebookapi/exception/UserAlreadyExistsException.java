package com.telran.phonebookapi.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends AbstractException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public UserAlreadyExistsException(String email) {
        super(String.format("User with email: %s already exists.", email));
    }
}
