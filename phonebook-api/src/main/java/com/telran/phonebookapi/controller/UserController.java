package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.exception.TokenNotFoundException;
import com.telran.phonebookapi.exception.UserAlreadyExistsException;
import com.telran.phonebookapi.service.ConfirmationTokenService;
import com.telran.phonebookapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @PostMapping("/api/v1/registration")
    public void create(@RequestBody @Valid UserDto userDto) throws UserAlreadyExistsException {
        userService.create(new User(userDto.email, userDto.password));
    }

    @GetMapping("api/v1/confirmation")
    public void confirm(@RequestParam(name = "token") String token) throws TokenNotFoundException {
        userService.confirmUser(token);
    }
}

