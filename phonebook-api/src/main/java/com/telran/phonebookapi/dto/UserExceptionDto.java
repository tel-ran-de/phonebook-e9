package com.telran.phonebookapi.dto;

import java.time.LocalDateTime;

public class UserExceptionDto {

    public String email;
    public String message;
    public LocalDateTime date = LocalDateTime.now();

    public UserExceptionDto(String email, String message) {
        this.email = email;
        this.message = message;
    }
}
