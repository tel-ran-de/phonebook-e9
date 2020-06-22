package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public void create(@RequestBody @Valid UserDto userDto) {
        userService.create(userDto.email, userDto.password);
    }

    @GetMapping("/confirmation")
    public void confirm(@RequestParam(name = "token") String token) {
        userService.confirmUser(token);
    }

    @PostMapping("/reset-password")
    public void confirmUpdatePass(@RequestBody @Valid UserDto userDto) {
        userService.createAndSendTokenForPassRecovery(userDto.email);
    }

    @PutMapping("/reset-password")
    public void updatePass(@RequestBody @Valid UserDto userDto, @RequestParam(value = "token") String token) {
        userService.updatePassword(token, userDto.password);
    }
}
