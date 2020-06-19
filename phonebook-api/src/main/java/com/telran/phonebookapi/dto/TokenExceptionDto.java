package com.telran.phonebookapi.dto;

import java.time.LocalDateTime;

public class TokenExceptionDto {

    public String message;
    public LocalDateTime date = LocalDateTime.now();

    public TokenExceptionDto(String message) {
        this.message = message;
    }
}
