package com.telran.phonebookapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    @Mock
    private MailSender javaMailSender;

    @InjectMocks
    EmailSender emailSender;

    @Captor
    ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @Test
    public void testCSendMail_oneTimeInvoked() {
        String userMail = "anna@gmail.com";
        String subject = "Mail Confirmation Link!";
        String ourMail = "no-reply@gmail.com";
        String text = "Thank you for your registration!";

        emailSender.send(userMail, subject, ourMail, text);

        verify(javaMailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage capturedMessage = messageCaptor.getValue();
        assertEquals(userMail, Objects.requireNonNull(capturedMessage.getTo())[0]);
        assertEquals(subject, capturedMessage.getSubject());
        assertEquals(ourMail, capturedMessage.getFrom());
        assertEquals(text, capturedMessage.getText());
    }
}
