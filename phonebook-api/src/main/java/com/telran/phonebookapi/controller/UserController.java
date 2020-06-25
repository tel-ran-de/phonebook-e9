package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.LoginRequestDto;
import com.telran.phonebookapi.dto.LoginResponseDto;
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

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequest) {
        return userService.login(loginRequest.email, loginRequest.password);
    }

/*    @PutMapping("/logout")
    public void logout(@RequestHeader("Authorization") String header) {

        UserSession userSession = userSessionRepository.findBySessionIdAndIsValidTrue(header);
        userSession.setIsValid(false);
        userSessionRepository.save(userSession);
    }*/
}
