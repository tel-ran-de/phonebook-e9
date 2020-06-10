package com.telran.phonebookapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {

    @Email
    public String email;
    @NotEmpty
    @NotNull
    @Size(max = 20, min = 8 /*, message = "{com.telran.person.first_name.validation.message}"*/)
    public String password;
}
