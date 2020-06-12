package com.telran.phonebookapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserDto {

    @Email(regexp = "[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,10}")
    public String email;

    @Size( min = 8, max = 20)
    public String password;
}
