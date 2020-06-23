package com.telran.phonebookapi.service;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.RecoveryPasswordToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.exception.TokenNotFoundException;
import com.telran.phonebookapi.exception.UserAlreadyExistsException;
import com.telran.phonebookapi.exception.UserNotActivatedException;
import com.telran.phonebookapi.persistence.IConfirmationTokenRepository;
import com.telran.phonebookapi.persistence.IRecoveryPasswordTokenRepo;
import com.telran.phonebookapi.persistence.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository userRepository;
    @Mock
    private IConfirmationTokenRepository confirmationTokenRepository;
    @Mock
    private IRecoveryPasswordTokenRepo recoveryPasswordTokenRepo;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private UserService userService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    public void testCreate_newUser_returnsOk() {
        String email = "jane.doe@gmail.com";
        String password = "fakePassword";
        userService.create(email, password);

        verify(userRepository, times(1)).findById(anyString());
        verify(bCryptPasswordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertEquals(email, capturedUser.getEmail());
        assertFalse(capturedUser.getIsConfirmed());
        verify(confirmationTokenRepository, times(1)).save(any());
        verify(emailSender, times(1)).send(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testCreate_userAlreadyExists_throwsException() {
        String email = "jane.doe@gmail.com";
        String password = "fakePassword";
        when(userRepository.findById(email)).thenThrow(new UserAlreadyExistsException(email));

        Exception ex = assertThrows(UserAlreadyExistsException.class, () -> userService.create(email, password));
        assertEquals("User with email: jane.doe@gmail.com already exists.", ex.getMessage());
        verify(userRepository, never()).save(any());
        verify(confirmationTokenRepository, never()).save(any());
        verify(emailSender, never()).send(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testConfirm_validToken_returnsOk() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        ConfirmationToken token = new ConfirmationToken(user);
        when(confirmationTokenRepository.findByToken(token.getToken())).thenReturn(Optional.of(token));
        userService.confirmUser(token.getToken());

        verify(confirmationTokenRepository, times(1)).findByToken(any());
        verify(userRepository, times(1)).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertEquals(user.getEmail(), capturedUser.getEmail());
        assertTrue(capturedUser.getIsConfirmed());
        verify(confirmationTokenRepository, times(1)).deleteById(any());
    }

    @Test
    public void testConfirm_nonValidToken_throwsException() {
        String token = "123456";
        when(confirmationTokenRepository.findByToken(token)).thenThrow(new TokenNotFoundException());

        Exception exception = assertThrows(TokenNotFoundException.class, () -> userService.confirmUser(token));
        assertEquals("Please sign up.", exception.getMessage());
        verify(userRepository, never()).save(userCaptor.capture());
        verify(confirmationTokenRepository, never()).deleteById(any());
    }

    @Test
    public void testPasswordRecovery_userNotActivatedAndNoTokenInDb_TokenNotFoundException() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        user.setIsConfirmed(false);
        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(TokenNotFoundException.class, () -> userService.createAndSendTokenForPassRecovery(user.getEmail()));

        assertEquals("Please sign up.", exception.getMessage());
        verify(userRepository, times(1)).findById(user.getEmail());
        verify(confirmationTokenRepository, times(1)).findByUserEmailIgnoreCase(user.getEmail());

        verify(emailSender, never()).send(anyString(), anyString(), anyString(), anyString());
        verify(confirmationTokenRepository, never()).save(any());
        verify(recoveryPasswordTokenRepo, never()).save(any());
    }

    @Test
    public void testPasswordRecovery_userNotActivatedValidToken_throwUserNotActivatedException() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        user.setIsConfirmed(false);
        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        ConfirmationToken token = new ConfirmationToken(user);
        when(confirmationTokenRepository.findByUserEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(token));

        Exception exception = assertThrows(UserNotActivatedException.class, () -> userService.createAndSendTokenForPassRecovery(user.getEmail()));

        assertEquals("User not Activated", exception.getMessage());
        verify(userRepository, times(1)).findById(user.getEmail());
        verify(confirmationTokenRepository, times(1)).findByUserEmailIgnoreCase(user.getEmail());
        verify(emailSender, times(1)).send(anyString(), anyString(), anyString(), anyString());
        verify(confirmationTokenRepository, never()).save(any());
        verify(recoveryPasswordTokenRepo, never()).save(any());
    }

    @Test
    public void testPasswordRecovery_userActivated_returnOk() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        user.setIsConfirmed(true);
        RecoveryPasswordToken recoveryPasswordToken = new RecoveryPasswordToken(user);
        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));

        userService.createAndSendTokenForPassRecovery(user.getEmail());

        verify(userRepository, times(1)).findById(user.getEmail());
        verify(confirmationTokenRepository, never()).findByUserEmailIgnoreCase(any());
        verify(confirmationTokenRepository, never()).save(any());
        verify(emailSender, times(1)).send(anyString(), anyString(), anyString(), anyString());
        verify(recoveryPasswordTokenRepo, times(1)).save(any(RecoveryPasswordToken.class));
    }

    @Test
    public void testPasswordUpdate_tokenNotFound_throwTokenNotFoundException() {
        String token = "token";
        String password = "password";

        when(recoveryPasswordTokenRepo.findByToken(token)).thenThrow(new TokenNotFoundException());
        Exception exception = assertThrows(TokenNotFoundException.class, () -> userService.updatePassword(token, password));

        assertEquals("Please sign up.", exception.getMessage());
        verify(recoveryPasswordTokenRepo, times(1)).findByToken(any());
        verify(bCryptPasswordEncoder, never()).encode(any());
        verify(recoveryPasswordTokenRepo, never()).deleteById(any());
    }

    @Test
    public void testPasswordUpdate_validToken_returnOk() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        user.setIsConfirmed(true);

        String token = "token";
        RecoveryPasswordToken recoveryPasswordToken = new RecoveryPasswordToken(user);
        String password = "password";

        when(recoveryPasswordTokenRepo.findByToken(token)).thenReturn(Optional.of(recoveryPasswordToken));
        userService.updatePassword(token, password);

        verify(recoveryPasswordTokenRepo, times(1)).findByToken(token);
        verify(bCryptPasswordEncoder, times(1)).encode(password);
        verify(recoveryPasswordTokenRepo, times(1)).deleteById(recoveryPasswordToken.getId());
    }
}
