package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.LoginResponseDto;
import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.entity.UserSession;
import com.telran.phonebookapi.exception.AuthenticationException;
import com.telran.phonebookapi.exception.TokenNotFoundException;
import com.telran.phonebookapi.exception.UserAlreadyExistsException;
import com.telran.phonebookapi.persistence.IConfirmationTokenRepository;
import com.telran.phonebookapi.persistence.IUserRepository;
import com.telran.phonebookapi.persistence.UserSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private static final String CONFORMATION_MAIL_SUBJECT = "Mail Confirmation Link!";
    private static final String CONFORMATION_MAIL_TEXT = "Thank you for your registration. Please click on the below link to activate your account. http://localhost:8080/api/user/confirmation?token=%s";
    private static final String OUR_MAIL = "<mail>";

    private final IUserRepository userRepository;
    private final IConfirmationTokenRepository confirmationTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSender emailSender;
    private final UserSessionRepository userSessionRepository;

    public void create(String email, String password) {
        final Optional<User> optionalUser = userRepository.findById(email.toLowerCase());

        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException(email);

        } else {
            final String encryptedPassword = bCryptPasswordEncoder.encode(password);
            User user = new User(email.toLowerCase(), encryptedPassword);
            userRepository.save(user);

            final ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);

            emailSender.send(user.getEmail(), CONFORMATION_MAIL_SUBJECT, OUR_MAIL, String.format(CONFORMATION_MAIL_TEXT, confirmationToken.getToken()));
        }
    }

    public void confirmUser(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(TokenNotFoundException::new);

        final User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        confirmationTokenRepository.deleteById(confirmationToken.getId());
    }

    public LoginResponseDto login(String email, String password) {
        User user = userRepository.findByEmailAndEnabledIsTrue(email.toLowerCase()).orElseThrow(AuthenticationException::new);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword()))
            throw new AuthenticationException();

        UserSession userSession = UserSession.builder()
                .accessToken(UUID.randomUUID().toString())
                .refreshToken(UUID.randomUUID().toString())
                .user(user)
                .isValid(true)
                .build();

        userSessionRepository.save(userSession);
        return new LoginResponseDto(userSession.getAccessToken(), userSession.getRefreshToken());
    }
}
