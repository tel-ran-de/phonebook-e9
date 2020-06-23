package com.telran.phonebookapi.service;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final String OUR_MAIL = "<mail>";

    private static final String CONFIRM_REGISTRATION_URL = "/user/activation/";
    private static final String RECOVERY_PASS_URL = "/user/reset-password?token=";

    private static final String CONFIRM_REGISTRATION_MAIL_SUBJECT = "Mail Confirmation Link!";
    private static final String CONFIRM_REGISTRATION_MAIL_TEXT = "Thank you for your registration. Please click on the below link to activate your account."
            + "http://localhost:4200" + CONFIRM_REGISTRATION_URL + "%s";

    private static final String RESEND_REGISTRATION_CONFORMATION_MAIL_TEXT = "You have received this letter since requested password recovery.\n" +
            "Due to the fact that the account is still not activated, password recovery is not possible.\n" +
            "Follow the link to activate your account and go through the password recovery process.\n"
            + "http://localhost:4200" + CONFIRM_REGISTRATION_URL + "%s";

    private static final String PASS_RECOVERY_MAIL_SUBJECT = "Password recovery";
    private static final String UPDATE_PASSWORD_CONFIRM_MAIL_TEXT = "To recover your password, follow the link http://localhost:4200" + RECOVERY_PASS_URL + "%s";

    private static final String USER_NOT_FOUND = "User not found";
    private static final String USER_NOT_ACTIVATED = "User not Activated";


    private final IUserRepository userRepository;
    private final IConfirmationTokenRepository confirmationTokenRepository;
    private final IRecoveryPasswordTokenRepo recoveryPasswordTokenRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSender emailSender;

    public UserService(IUserRepository userRepository,
                       IConfirmationTokenRepository confirmationTokenRepository,
                       IRecoveryPasswordTokenRepo recoveryPasswordTokenRepo,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       EmailSender emailSender) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.recoveryPasswordTokenRepo = recoveryPasswordTokenRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSender = emailSender;
    }

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

            emailSender.send(user.getEmail(), CONFIRM_REGISTRATION_MAIL_SUBJECT, OUR_MAIL, String.format(CONFIRM_REGISTRATION_MAIL_TEXT, confirmationToken.getToken()));
        }
    }

    public void confirmUser(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(TokenNotFoundException::new);

        final User user = confirmationToken.getUser();
        user.setIsConfirmed(true);
        userRepository.save(user);

        confirmationTokenRepository.deleteById(confirmationToken.getId());
    }

    public void createAndSendTokenForPassRecovery(String email) {
        Optional<User> userOptional = userRepository.findById(email);

        User user = userOptional.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!user.getIsConfirmed()) {
            ConfirmationToken confirmationToken = confirmationTokenRepository.findByUserEmailIgnoreCase(user.getEmail()).orElseThrow(TokenNotFoundException::new);
            emailSender.send(user.getEmail(), PASS_RECOVERY_MAIL_SUBJECT, OUR_MAIL, String.format(RESEND_REGISTRATION_CONFORMATION_MAIL_TEXT, confirmationToken.getToken()));
            throw new UserNotActivatedException(USER_NOT_ACTIVATED);
        } else {
            RecoveryPasswordToken recoveryPasswordToken = new RecoveryPasswordToken(user);
            recoveryPasswordTokenRepo.save(recoveryPasswordToken);
            emailSender.send(user.getEmail(), PASS_RECOVERY_MAIL_SUBJECT, OUR_MAIL, String.format(UPDATE_PASSWORD_CONFIRM_MAIL_TEXT, recoveryPasswordToken.getToken()));
        }
    }

    public void updatePassword(String token, String password) {
        RecoveryPasswordToken recoveryPasswordToken = recoveryPasswordTokenRepo.findByToken(token).orElseThrow(TokenNotFoundException::new);

        final User user = recoveryPasswordToken.getUser();
        final String encryptedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encryptedPassword);

        recoveryPasswordTokenRepo.deleteById(recoveryPasswordToken.getId());
    }
}
