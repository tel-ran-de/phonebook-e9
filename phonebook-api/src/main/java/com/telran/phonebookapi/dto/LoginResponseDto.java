package com.telran.phonebookapi.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponseDto {
    public String accessToken;
    public String refreshToken;
}
