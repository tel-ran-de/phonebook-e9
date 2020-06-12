package com.telran.phonebookapi.service;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.exception.TokenNotFoundException;
import com.telran.phonebookapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    private JavaMailSender javaMailSender;

    @Test
    public void testCreate_newUser_returnsOk() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        ConfirmationToken token = new ConfirmationToken(user);
        userRepository.save(user);
        confirmationTokenService.save(token);

        verify(userRepository, times(1)).save(any());
        verify(confirmationTokenService, times(1)).save(any());
    }

    @Test
    public void testConfirm_validToken_returnsOk() throws TokenNotFoundException {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        user.setEnabled(true);
        ConfirmationToken token = new ConfirmationToken(user);
        confirmationTokenService.findByToken(token.getToken());
        userRepository.save(user);
        confirmationTokenService.delete(token.getId());

        verify(confirmationTokenService, times(1)).findByToken(any());
        verify(userRepository, times(1)).save(any());
        verify(confirmationTokenService, times(1)).delete(any());
    }
}
