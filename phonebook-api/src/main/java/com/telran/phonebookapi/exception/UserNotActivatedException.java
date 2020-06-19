package com.telran.phonebookapi.exception;

public class UserNotActivatedException extends RuntimeException {

    public UserNotActivatedException(String email) {
        super("User with email: " + email + " is not activated. We sent again the instructions for activating the account to the specified email address.");
    }
}
