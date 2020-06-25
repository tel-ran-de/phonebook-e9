package com.telran.phonebookapi.controller;


import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.persistence.IConfirmationTokenRepository;
import com.telran.phonebookapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

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
    private IConfirmationTokenRepository confirmationTokenRepository;

    @Test
    public void testCreate_returnsOk() throws Exception {
        mvc.perform(post("/api/user/registration")
                .content("{\"email\": \"ivan@mail.com\",\"password\":\"pet8rov34j\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(service, times(1)).create(anyString(), anyString());
    }

    @Test
    public void testCreate_shortPassword_returns400() throws Exception {
        mvc.perform(post("/api/user/registration")
                .content("{\"email\": \"ivan@mail.com\",\"password\":\"pet\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(service, never()).create(anyString(), anyString());
    }

    @Test
    public void testCreate_longPassword_returns400() throws Exception {
        mvc.perform(post("/api/user/registration")
                .content("{\"email\": \"ivan@mail.com\",\"password\":\"pesdfdfdgdgfgfgfhfhgfhfght\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(service, never()).create(anyString(), anyString());
    }

    @Test
    public void testCreate_notValidEmailSecondPart_returns400() throws Exception {
        mvc.perform(post("/api/user/registration")
                .content("{\"email\": \"ivan@mailcom\",\"password\":\"pegfhfhgfhfght\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(service, never()).create(anyString(), anyString());
    }

    @Test
    public void testCreate_notValidEmail_returns400() throws Exception {
        mvc.perform(post("/api/user/registration")
                .content("{\"email\": \"ivanmail.com\",\"password\":\"pegfhfhgfhfght\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(service, never()).create(anyString(), anyString());
    }

    @Test
    public void testCreate_EmailWithForbiddenChar_returns400() throws Exception {
        mvc.perform(post("/api/user/registration")
                .content("{\"email\": \"~%anna@gmail.com*&^%\",\"password\":\"pegfhfhgfhfght\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(service, never()).create(anyString(), anyString());
    }

    @Test
    public void testConfirm_validToken_returnsOk() throws Exception {
        User user = new User("anna@gmail.com", "dfdfdfgfgsr");
        ConfirmationToken token = new ConfirmationToken(user, UUID.randomUUID().toString());

        when(confirmationTokenRepository.findByToken("1500000"))
                .thenReturn(Optional.of(token));
        mvc.perform(get("/api/user/confirmation?token=1500000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).confirmUser(any());
    }

    @Test
    public void testDto_emailNull_result400() throws Exception {
        mvc.perform(post("/api/user/registration")
                .content("{\"password\":\"pegfhfhgfhfght\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(service, never()).create(anyString(), anyString());
    }

    @Test
    public void testDto_passwordNull_return400() throws Exception {
        mvc.perform(post("/api/user/registration")
                .content("{\"email\":\"valid.mail@gmail.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(service, never()).create(anyString(), anyString());
    }
}
