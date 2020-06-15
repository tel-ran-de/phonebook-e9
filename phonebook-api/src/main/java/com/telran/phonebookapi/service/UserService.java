package com.telran.phonebookapi.service;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.exception.TokenNotFoundException;
import com.telran.phonebookapi.exception.UserAlreadyExistsException;
import com.telran.phonebookapi.repository.ConfirmationTokenRepository;
import com.telran.phonebookapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private static final String CONFORMATION_MAIL_SUBJECT = "Mail Confirmation Link!";
    private static final String CONFORMATION_MAIL_TEXT = "Thank you for your registration. Please click on the below link to activate your account. http://localhost:8080/api/user/confirmation?token=%s";
    private static final String OUR_MAIL = "<mail>";

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSender emailSender;

    public void create(String email, String password) {
        final Optional<User> optionalUser = userRepository.findById(email);

        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException(email);

        } else {
            final String encryptedPassword = bCryptPasswordEncoder.encode(password);
            User user = new User(email, encryptedPassword);
            userRepository.save(user);

            final ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);

            emailSender.send(user.getEmail(), CONFORMATION_MAIL_SUBJECT, OUR_MAIL, String.format(CONFORMATION_MAIL_TEXT, confirmationToken.getToken()));
        }
    }

    public void confirmUser(String  token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(TokenNotFoundException::new);

        final User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        confirmationTokenRepository.deleteById(confirmationToken.getId());
    }
}
