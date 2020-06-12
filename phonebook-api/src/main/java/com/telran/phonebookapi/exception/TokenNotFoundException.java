package com.telran.phonebookapi.exception;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException() {
        super("Token not found.");
    }
}
