package com.telran.phonebookapi.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;

    @Async
    public void send(String userMail, String subject, String ourMail, String text) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject(subject);
        mailMessage.setFrom(ourMail);
        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
    }
}
