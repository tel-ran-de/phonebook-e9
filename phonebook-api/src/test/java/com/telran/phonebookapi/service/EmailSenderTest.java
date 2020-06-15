package com.telran.phonebookapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void testCSendMail_oneTimeInvoked() {

        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("anna@gmail.com");
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("no-reply@gmail.com");
        mailMessage.setText("Thank you for your registration!");
        javaMailSender.send(mailMessage);

        verify(javaMailSender, times(1)).send(mailMessage);
    }
}
