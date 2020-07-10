package com.telran.phonebookapi.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String jwtAccessToken;
   // private String jwtRefreshToken;
}
