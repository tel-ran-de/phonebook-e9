package com.telran.phonebookapi.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("User with email: " + message + " not found");
    }
}
