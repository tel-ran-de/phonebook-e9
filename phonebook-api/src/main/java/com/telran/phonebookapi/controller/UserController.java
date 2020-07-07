package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.UserRegisterDto;
import com.telran.phonebookapi.dto.UserResetPassDto;
import com.telran.phonebookapi.dto.UserResetPassEmailDto;
import com.telran.phonebookapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping()
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user/registration")
    public void create(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        userService.create(userRegisterDto.email, userRegisterDto.password);
    }

    @GetMapping("/api/user/confirmation")
    public void confirm(@RequestParam(name = "token") String token) {
        userService.confirmUser(token);
    }

    @PostMapping("/api/user/reset-password")
    public void confirmUpdatePass(@RequestBody @Valid UserResetPassEmailDto USerResetPassEmailDto) {
        userService.createAndSendTokenForPassRecovery(USerResetPassEmailDto.email);
    }

    @PutMapping("/api/user/password")
    public void updatePass(@RequestBody @Valid UserResetPassDto passwordDto, @RequestParam(value = "token") String token) {
        userService.updatePassword(token, passwordDto.password);
    }

}

