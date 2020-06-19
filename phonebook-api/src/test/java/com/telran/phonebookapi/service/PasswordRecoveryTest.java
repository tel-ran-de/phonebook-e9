package com.telran.phonebookapi.service;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.exception.UserNotActivatedException;
import com.telran.phonebookapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasswordRecoveryTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ConfirmationTokenService confirmationTokenService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private JavaMailSender javaMailSender;
    @Autowired
    private UserService service;
    @Autowired
    TestEntityManager entityManager;

    @Test
    public void createNewTokenForRecoveryPass__returnsUserNotActivated() {
        User user = new User("user@mail.com", "encryptedOldPassword");
        user.setEnabled(false);

        ConfirmationToken token = new ConfirmationToken(user);
        confirmationTokenService.findByToken(token.getToken());

        userRepository.save(user);

        service.creatAndSendTokenForPassRecovery(user.getEmail());

        when(service).thenThrow(UserNotActivatedException.class);

    }
}
