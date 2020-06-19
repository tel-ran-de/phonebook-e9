package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/user/pass-recovery")
    public void confirmUpdatePass(@RequestBody @Valid UserDto userDto) {
        userService.creatAndSendTokenForPassRecovery(userDto.email);
    }

    @PutMapping("/api/v1/user/pass-update")
    public void updatePass(@RequestBody @Valid UserDto userDto, @RequestParam(value = "token") String token) {
        userService.updatePassword(token, userDto.password);
    }


    @PostMapping("/api/v1/user/registration")
    public void create(@RequestBody @Valid UserDto userDto) {
        userService.create(new User(userDto.email, userDto.password));
    }

    @GetMapping("api/v1/user/confirmation")
    public void confirm(@RequestParam(name = "token") String token) {
        userService.confirmUser(token);
    }
}


