package com.telran.phonebookapi.service;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.exception.TokenNotFoundException;
import com.telran.phonebookapi.exception.UserAlreadyExistsException;
import com.telran.phonebookapi.repository.ConfirmationTokenRepository;
import com.telran.phonebookapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private EmailSender emailSender;

    @Test
    public void testCreate_newUser_returnsOk() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        ConfirmationToken token = new ConfirmationToken(user);
        userRepository.save(user);
        confirmationTokenRepository.save(token);
        emailSender.send(user.getEmail(), "Token", "our@gmail.com", "Hi");

        verify(userRepository, times(1)).save(any());
        verify(confirmationTokenRepository, times(1)).save(any());
        verify(emailSender, times(1)).send(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testCreate_userAlreadyExists_throwsException() {
        User user = new User("john@gmail.com", "sdfdsfdfgs");
        when(userRepository.findById(user.getEmail())).thenThrow(UserAlreadyExistsException.class);

        assertThrows(UserAlreadyExistsException.class, () -> userRepository.findById(user.getEmail()));
    }

    @Test
    public void testConfirm_validToken_returnsOk() throws TokenNotFoundException {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        user.setEnabled(true);
        ConfirmationToken token = new ConfirmationToken(user);
        confirmationTokenRepository.findByToken(token.getToken());
        userRepository.save(user);
        confirmationTokenRepository.deleteById(token.getId());

        verify(confirmationTokenRepository, times(1)).findByToken(any());
        verify(userRepository, times(1)).save(any());
        verify(confirmationTokenRepository, times(1)).deleteById(any());
    }

    @Test
    public void testConfirm_nonValidToken_throwsException() {
        User user = new User("john@gmail.com", "sdfdsfdfgs");
        ConfirmationToken token = new ConfirmationToken(user);
        when(confirmationTokenRepository.findByToken(token.getToken())).thenThrow(TokenNotFoundException.class);

        assertThrows(TokenNotFoundException.class, () -> confirmationTokenRepository.findByToken(token.getToken()));
    }
}
