package com.telran.phonebookapi.service;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.repository.UserRepository;
import com.telran.phonebookapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ConfirmationTokenService confirmationTokenService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void testCreate_newUser_returnsOk() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        ConfirmationToken token = new ConfirmationToken(1L, "1500000", LocalDate.now(), new User("anna@gmail.com", "lkjsdfwe2ex"));
        userRepository.save(user);
        confirmationTokenService.save(token);

        verify(userRepository, times(1)).save(any());
        verify(confirmationTokenService, times(1)).save(any());
    }

    @Test
    public void testConfirm_validToken_returnsOk() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        user.setEnabled(true);
        ConfirmationToken token = new ConfirmationToken(1L, "1500000", LocalDate.now(), new User("anna@gmail.com", "lkjsdfwe2ex"));
        userRepository.save(user);
        confirmationTokenService.delete(token.getId());

        verify(userRepository, times(1)).save(any());
        verify(confirmationTokenService, times(1)).delete(any());
    }
}
