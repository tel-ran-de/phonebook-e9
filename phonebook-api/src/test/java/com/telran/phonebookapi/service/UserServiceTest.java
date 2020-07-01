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
import java.util.UUID;

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
    @Captor
    ArgumentCaptor<RecoveryPasswordToken> recoveryPasswordTokenCaptor;
    @Captor
    ArgumentCaptor<ConfirmationToken> confirmationTokenCaptor;

    @Test
    public void testCreate_newUser_returnsOk() {
        String email = "jane.doe@gmail.com";
        String password = "fakePassword";
        userService.ourMail = "<email>";
        userService.hostUi = "http://localhost:4200";
        userService.create(email, password);

        verify(userRepository, times(1)).findById(anyString());
        verify(bCryptPasswordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(userCaptor.capture());

        verify(confirmationTokenRepository).save(confirmationTokenCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals(email, capturedUser.getEmail());
        assertFalse(capturedUser.isConfirmed());
        verify(confirmationTokenRepository, times(1)).save(any());
        verify(emailSender, times(1)).send("jane.doe@gmail.com",
                "Mail Confirmation Link!",
                "<email>",
                "Thank you for your registration. Please click on the below link to activate your account. http://localhost:4200/user/activation/" + confirmationTokenCaptor.getValue().getToken());
    }

    @Test
    public void testCreate_userAlreadyExists_throwsException() {
        String email = "jane.doe@gmail.com";
        String password = "fakePassword";

        String userExists = "User with email: %s already exists.";
        when(userRepository.findById(email)).thenThrow(new UserAlreadyExistsException(String.format(userExists, email)));

        Exception ex = assertThrows(UserAlreadyExistsException.class, () -> userService.create(email, password));
        assertEquals("User with email: jane.doe@gmail.com already exists.", ex.getMessage());

        verify(userRepository, never()).save(any());
        verify(confirmationTokenRepository, never()).save(any());
        verify(emailSender, never()).send(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testConfirm_validToken_returnsOk() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        String tokenToSet = tokenGenerate();
        ConfirmationToken token = new ConfirmationToken(user, tokenToSet);

        when(confirmationTokenRepository.findByToken(token.getToken())).thenReturn(Optional.of(token));

        userService.confirmUser(token.getToken());

        verify(confirmationTokenRepository, times(1)).findByToken(tokenToSet);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assertEquals(user.getEmail(), capturedUser.getEmail());
        assertTrue(capturedUser.isConfirmed());
        verify(confirmationTokenRepository, times(1)).deleteById(token.getId());
    }

    @Test
    public void testConfirm_nonValidToken_throwsException() {
        String token = "123456";
        when(confirmationTokenRepository.findByToken(token)).thenThrow(new TokenNotFoundException("Please sign up."));

        Exception exception = assertThrows(TokenNotFoundException.class, () -> userService.confirmUser(token));

        assertEquals("Please sign up.", exception.getMessage());
        verify(userRepository, never()).save(userCaptor.capture());
        verify(confirmationTokenRepository, never()).deleteById(any());
    }

    @Test
    public void testPasswordRecovery_userNotActivatedAndNoTokenInDb_TokenNotFoundException() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        user.setConfirmed(false);
        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(TokenNotFoundException.class, () -> userService.createAndSendTokenForPassRecovery(user.getEmail()));

        assertEquals("Link expired", exception.getMessage());
        verify(userRepository, times(1)).findById(user.getEmail());
        verify(confirmationTokenRepository, times(1)).findByUserEmailIgnoreCase(user.getEmail());

        verify(emailSender, never()).send(anyString(), anyString(), anyString(), anyString());
        verify(confirmationTokenRepository, never()).save(any());
        verify(recoveryPasswordTokenRepo, never()).save(any());
    }

    @Test
    public void testPasswordRecovery_userNotActivatedValidToken_throwUserNotActivatedException() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        user.setConfirmed(false);
        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        String tokenToSet = tokenGenerate();
        ConfirmationToken token = new ConfirmationToken(user, tokenToSet);

        userService.ourMail = "<email>";
        userService.hostUi = "http://localhost:4200";

        when(confirmationTokenRepository.findByUserEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(token));

        Exception exception = assertThrows(UserNotActivatedException.class, () -> userService.createAndSendTokenForPassRecovery(user.getEmail()));

        assertEquals("User not Activated", exception.getMessage());

        verify(userRepository, times(1)).findById(user.getEmail());
        verify(confirmationTokenRepository, times(1)).findByUserEmailIgnoreCase(user.getEmail());
        verify(emailSender, times(1)).send(user.getEmail(),
                "Password recovery",
                "<email>",
                "You have received this letter since requested password recovery.\n" +
                        "Due to the fact that the account is still not activated, password recovery is not possible.\n" +
                        "Follow the link to activate your account and go through the password recovery process.\n" +
                        "http://localhost:4200/user/activation/" + tokenToSet);
        verify(emailSender, times(1)).send(anyString(), anyString(), anyString(), anyString());
        verify(confirmationTokenRepository, never()).save(any());
        verify(recoveryPasswordTokenRepo, never()).save(any());
    }


    @Test
    public void testPasswordRecovery_userActivated_returnOk() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        user.setConfirmed(true);

        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        userService.ourMail = "<email>";
        userService.hostUi = "http://localhost:4200";
        userService.createAndSendTokenForPassRecovery(user.getEmail());

        verify(recoveryPasswordTokenRepo).save(recoveryPasswordTokenCaptor.capture());

        verify(userRepository, times(1)).findById(user.getEmail());
        verify(confirmationTokenRepository, never()).findByUserEmailIgnoreCase(any());

        verify(emailSender, times(1)).send(user.getEmail(),
                "Password recovery",
                "<email>",
                "To recover your password, follow the link http://localhost:4200/user/reset-password?token="
                        + recoveryPasswordTokenCaptor.getValue().getToken());

        verify(recoveryPasswordTokenRepo, times(1)).save(recoveryPasswordTokenCaptor.getValue());
    }

    @Test
    public void testPasswordUpdate_tokenNotFound_throwTokenNotFoundException() {
        String token = "token";
        String password = "password";

        when(recoveryPasswordTokenRepo.findByToken(token)).thenThrow(new TokenNotFoundException("Please sign up."));
        Exception exception = assertThrows(TokenNotFoundException.class, () -> userService.updatePassword(token, password));

        assertEquals("Please sign up.", exception.getMessage());
        verify(recoveryPasswordTokenRepo, times(1)).findByToken(token);
        verify(bCryptPasswordEncoder, never()).encode(any());
        verify(recoveryPasswordTokenRepo, never()).deleteById(any());
    }

    @Test
    public void testPasswordUpdate_validToken_returnOk() {
        User user = new User("anna@gmail.com", "kjkjsdfsdfdf");
        user.setConfirmed(true);

        String tokenToSet = UUID.randomUUID().toString();
        RecoveryPasswordToken recoveryPasswordToken = new RecoveryPasswordToken(user, tokenToSet);
        String password = "password";

        when(recoveryPasswordTokenRepo.findByToken(tokenToSet)).thenReturn(Optional.of(recoveryPasswordToken));

        userService.updatePassword(tokenToSet, password);

        verify(recoveryPasswordTokenRepo, times(1)).findByToken(tokenToSet);
        verify(bCryptPasswordEncoder, times(1)).encode(password);
        verify(recoveryPasswordTokenRepo, times(1)).deleteById(recoveryPasswordToken.getId());
        verify(emailSender, never()).send(anyString(), anyString(), anyString(), anyString());
    }

    private String tokenGenerate() {
        return UUID.randomUUID().toString();
    }
}
