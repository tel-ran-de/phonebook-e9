package com.telran.phonebookapi.controller;


import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.service.ConfirmationTokenService;
import com.telran.phonebookapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;
    @MockBean
    private ConfirmationTokenService tokenService;

    @Test
    public void testCreate_newUser_returnsOk() throws Exception {
        mvc.perform(post("/api/v1/registration")
                .content("{\"email\": \"ivan@mail.com\",\"password\":\"pet8rov34j\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(service, times(1)).create(any());
    }

    @Test
    public void testConfirm_validToken_returnsOk() throws Exception {
        ConfirmationToken token = new ConfirmationToken(1L, "1500000", LocalDate.now(), new User("anna@gmail.com", "lkjsdfwe2ex"));

        when(tokenService.findByToken("1500000"))
                .thenReturn(Optional.of(token));
        mvc.perform(get("/api/v1/confirmation?token=1500000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tokenService, times(1)).findByToken("1500000");
        verify(service, times(1)).confirmUser(tokenService.findByToken("1500000").get());
    }
}
