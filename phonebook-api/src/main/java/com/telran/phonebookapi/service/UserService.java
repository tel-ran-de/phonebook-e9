package com.telran.phonebookapi.service;

import com.telran.phonebookapi.config.AuthResponse;
import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.RecoveryPasswordToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.exception.TokenNotFoundException;
import com.telran.phonebookapi.exception.UserAlreadyExistsException;
import com.telran.phonebookapi.exception.UserNotActivatedException;
import com.telran.phonebookapi.exception.UserNotFoundException;
import com.telran.phonebookapi.persistence.IConfirmationTokenRepository;
import com.telran.phonebookapi.persistence.IRecoveryPasswordTokenRepo;
import com.telran.phonebookapi.persistence.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {


    @Value("${com.telran.phonebookapi.our.mail}")
    protected String ourMail;

    @Value("${com.telran.phonebookapi.ui.host}")
    protected String hostUi;

    private static final String CONFIRM_REGISTRATION_URL = "/user/activation/";
    private static final String RECOVERY_PASS_URL = "/user/reset-password?token=";

    private static final String CONFIRM_REGISTRATION_MAIL_SUBJECT = "Mail Confirmation Link!";
    private static final String CONFIRM_REGISTRATION_MAIL_TEXT = "Thank you for your registration. Please click on the below link to activate your account. "
            + "%s" + CONFIRM_REGISTRATION_URL + "%s";

    private static final String RESEND_REGISTRATION_CONFORMATION_MAIL_TEXT = "You have received this letter since requested password recovery.\n" +
            "Due to the fact that the account is still not activated, password recovery is not possible.\n" +
            "Follow the link to activate your account and go through the password recovery process.\n"
            + "%s" + CONFIRM_REGISTRATION_URL + "%s";

    private static final String PASS_RECOVERY_MAIL_SUBJECT = "Password recovery";
    private static final String UPDATE_PASSWORD_CONFIRM_MAIL_TEXT = "To recover your password, follow the link " + "%s" + RECOVERY_PASS_URL + "%s";

    private static final String TOKEN_NOT_FOUND = "Link expired";
    private static final String USER_NOT_FOUND = "User not found";
    private static final String USER_NOT_ACTIVATED = "User not Activated";
    private static final String USER_ALREADY_EXISTS = "User with email: %s already exists.";

    private final IUserRepository userRepository;
    private final IConfirmationTokenRepository confirmationTokenRepository;
    private final IRecoveryPasswordTokenRepo recoveryPasswordTokenRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSender emailSender;
    private JWTUtil jwtTokenUtil;

    public UserService(
            IUserRepository userRepository,
            IConfirmationTokenRepository confirmationTokenRepository,
            IRecoveryPasswordTokenRepo recoveryPasswordTokenRepo,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            EmailSender emailSender, JWTUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.recoveryPasswordTokenRepo = recoveryPasswordTokenRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSender = emailSender;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public void create(String email, String password) {
        final Optional<User> optionalUser = userRepository.findById(email.toLowerCase());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS, email));

        } else {
            final String encryptedPassword = bCryptPasswordEncoder.encode(password);
            User user = new User(email.toLowerCase(), encryptedPassword);
            userRepository.save(user);

            final ConfirmationToken confirmationToken = new ConfirmationToken(user, UUID.randomUUID().toString());
            confirmationTokenRepository.save(confirmationToken);

            emailSender.send(user.getEmail(), CONFIRM_REGISTRATION_MAIL_SUBJECT, ourMail, String.format(CONFIRM_REGISTRATION_MAIL_TEXT, hostUi, confirmationToken.getToken()));
        }
    }

    public void confirmUser(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(() -> new TokenNotFoundException(TOKEN_NOT_FOUND));

        final User user = confirmationToken.getUser();
        user.setConfirmed(true);
        userRepository.save(user);

        confirmationTokenRepository.deleteById(confirmationToken.getId());
    }

    public void createAndSendTokenForPassRecovery(String email) {
        Optional<User> userOptional = userRepository.findById(email);

        User user = userOptional.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!user.isConfirmed()) {
            ConfirmationToken confirmationToken = confirmationTokenRepository.findByUserEmailIgnoreCase(user.getEmail()).orElseThrow(() -> new TokenNotFoundException(TOKEN_NOT_FOUND));

            emailSender.send(user.getEmail(), PASS_RECOVERY_MAIL_SUBJECT, ourMail, String.format(RESEND_REGISTRATION_CONFORMATION_MAIL_TEXT, hostUi, confirmationToken.getToken()));
            throw new UserNotActivatedException(USER_NOT_ACTIVATED);
        } else {
            RecoveryPasswordToken recoveryPasswordToken = new RecoveryPasswordToken(user, UUID.randomUUID().toString());
            recoveryPasswordTokenRepo.save(recoveryPasswordToken);

            emailSender.send(user.getEmail(), PASS_RECOVERY_MAIL_SUBJECT, ourMail, String.format(UPDATE_PASSWORD_CONFIRM_MAIL_TEXT, hostUi, recoveryPasswordToken.getToken()));
        }
    }

    public void updatePassword(String token, String password) {
        RecoveryPasswordToken recoveryPasswordToken = recoveryPasswordTokenRepo.findByToken(token).orElseThrow(() -> new TokenNotFoundException(TOKEN_NOT_FOUND));

        final User user = recoveryPasswordToken.getUser();
        final String encryptedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encryptedPassword);

        recoveryPasswordTokenRepo.deleteById(recoveryPasswordToken.getId());
    }

    public void login(String email, String password) {
        final Optional<User> optionalUser = userRepository.findById(email.toLowerCase());
        if (optionalUser.isPresent()) {
            final String encryptedPassword = bCryptPasswordEncoder.encode(password);
         if (encryptedPassword.equals(optionalUser.get().getPassword())) {
                String jwtAccess = jwtTokenUtil.generateAccessToken(email);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Имя или пароль неправильны");
            }
        } else {
            throw new UserNotFoundException(String.format(USER_NOT_FOUND, email));

        }
    }
}
