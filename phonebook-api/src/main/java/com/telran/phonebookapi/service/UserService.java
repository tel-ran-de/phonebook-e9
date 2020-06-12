package com.telran.phonebookapi.service;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.exception.TokenNotFoundException;
import com.telran.phonebookapi.exception.UserAlreadyExistsException;
import com.telran.phonebookapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    public void create(User user) throws UserAlreadyExistsException {
        final Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException(MessageFormat.format("User with email {0} is already exists.", user.getEmail()));

        } else {
            final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            final User createdUser = userRepository.save(user);

            final ConfirmationToken confirmationToken = new ConfirmationToken(createdUser);

            confirmationTokenService.save(confirmationToken);

            sendConfirmationMail(user.getEmail(), confirmationToken.getToken());
        }
    }

    public void confirmUser(String  token) throws TokenNotFoundException {
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);

        final User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        confirmationTokenService.delete(confirmationToken.getId());
    }

    private void sendConfirmationMail(String userMail, String token) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Thank you for your registration. Please click on the below link to activate your account. " + "http://localhost:8080/api/v1/confirmation?token="
                        + token);

        sendEmail(mailMessage);
    }

    private void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
