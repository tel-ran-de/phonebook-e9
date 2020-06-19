package com.telran.phonebookapi.service;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.exception.TokenNotFoundException;
import com.telran.phonebookapi.exception.UserAlreadyExistsException;
import com.telran.phonebookapi.exception.UserNotActivatedException;
import com.telran.phonebookapi.exception.UserNotFoundException;
import com.telran.phonebookapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {

    final String HOST = "http://localhost:4200";

    final String CONFIRM_REGISTRATION_URL = "/user/activation/";
    final String HOST_RECOVERY_URL = "/user/new-password/token=";

    final String REGISTRATION_ACTIVATE_MESSAGE = "Thank you for your registration. Please click on the below link to activate your account. " + HOST + CONFIRM_REGISTRATION_URL;

    final String RESEND_REGISTRATION_ACTIVATE_MESSAGE = "You have received this letter since requested password recovery.\n" +
            " Due to the fact that the account is still not activated, password recovery is not possible.\n" +
            "Follow the link to activate your account and go through the password recovery process.\n"
            + HOST + CONFIRM_REGISTRATION_URL;

    final String UPDATE_PASSWORD_CONFIRM_MESSAGE = "To recover your password, follow the link " + HOST + HOST_RECOVERY_URL;
    final String MAIL_TITLE_REGISTRATION = "Mail Confirmation Link!";
    final String MAIL_TITLE_PASS_RECOVERY = "Password recovery";


    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;


    public void creatAndSendTokenForPassRecovery(String email) {
        Optional<User> userOptional = userRepository.findById(email);

        User user = userOptional.
                orElseThrow(() -> new UserNotFoundException(email));

        if (!user.getEnabled()) {
            ConfirmationToken confirmationToken = confirmationTokenService.findByEmail(user.getEmail());
            sendConfirmationMail(user.getEmail(), confirmationToken.getToken(), RESEND_REGISTRATION_ACTIVATE_MESSAGE, MAIL_TITLE_PASS_RECOVERY);
            throw new UserNotActivatedException(user.getEmail());
        } else {
            final ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenService.save(confirmationToken);
            sendConfirmationMail(user.getEmail(), confirmationToken.getToken(), UPDATE_PASSWORD_CONFIRM_MESSAGE, MAIL_TITLE_PASS_RECOVERY);
        }
    }

    public void updatePassword(String token, String password) {

        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);

        if (confirmationToken == null)
            throw new TokenNotFoundException();

        final User user = confirmationToken.getUser();
        final String encryptedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encryptedPassword);

        confirmationTokenService.delete(confirmationToken.getId());
    }


    public void create(User user) {
        final Optional<User> optionalUser = userRepository.findById(user.getEmail());

        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());

        } else {
            final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            final User createdUser = userRepository.save(user);

            final ConfirmationToken confirmationToken = new ConfirmationToken(createdUser);

            confirmationTokenService.save(confirmationToken);

            sendConfirmationMail(user.getEmail(), confirmationToken.getToken(), REGISTRATION_ACTIVATE_MESSAGE, MAIL_TITLE_REGISTRATION);
        }
    }

    public void confirmUser(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);

        final User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        confirmationTokenService.delete(confirmationToken.getId());
    }


    void sendConfirmationMail(String userMail, String token, String message, String mailTitle) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject(mailTitle);
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(message + token);

        sendEmail(mailMessage);
    }

    @Async
    void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}

