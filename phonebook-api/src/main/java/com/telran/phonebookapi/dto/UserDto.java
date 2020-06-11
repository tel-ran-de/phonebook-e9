package com.telran.phonebookapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserDto {

    @Email
    public String email;

    @Size( min = 8, max = 20)
    public String password;
}
