package com.telran.phonebookapi.exception;

import lombok.Getter;

public class UserAlreadyExistsException extends RuntimeException {

    @Getter
    private final String email;

    public UserAlreadyExistsException(String email) {
        super("User already exists.");
        this.email = email;
    }
}
