package com.telran.phonebookapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserDto {

    @Email(regexp = "[a-zA-Z0-9!#$%&'*+/=?^\\-_`{|}~.]+@[a-zA-Z_]+?\\.[a-zA-Z]{2,10}")
    public String email;

    @Size( min = 8, max = 20)
    public String password;
}
