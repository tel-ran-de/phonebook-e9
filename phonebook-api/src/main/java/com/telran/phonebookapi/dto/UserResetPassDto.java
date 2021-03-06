package com.telran.phonebookapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserResetPassDto {

    @Size( min = 8, max = 20)
    @NotEmpty
    public String password;
}
