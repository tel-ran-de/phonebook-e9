package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.entity.Contact;
import com.telran.phonebookapi.entity.Email;
import com.telran.phonebookapi.mapper.EmailMapper;
import com.telran.phonebookapi.persistence.IContactRepo;
import com.telran.phonebookapi.persistence.IEmailRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {
    final static String EMAIL_NOT_FOUND = "Email not found";

    final private IEmailRepo emailRepo;
    final private IContactRepo contactRepo;
    final private EmailMapper emailMapper;

    public EmailService(IEmailRepo emailRepo, IContactRepo contactRepo, EmailMapper emailMapper) {
        this.emailRepo = emailRepo;
        this.contactRepo = contactRepo;
        this.emailMapper = emailMapper;
    }

    public void create(EmailDto emailDto) {
        Contact contact = contactRepo.findById(emailDto.contactId).orElseThrow(
                () -> new EntityNotFoundException(ContactService.CONTACT_NOT_FOUND));
        Email email = new Email(contact, emailDto.email, emailDto.type);
        emailRepo.save(email);
    }

    public void edit(EmailDto emailDto) {
        Email email = emailRepo.findById(emailDto.id).orElseThrow(
                ()-> new EntityNotFoundException(EMAIL_NOT_FOUND));
        email.setEmail(emailDto.email);
    }

    public void removeById(int id) {
        emailRepo.deleteById(id);
    }

    public List<EmailDto> getAllByContact(int contactId) {
        List<Email> emails = emailRepo.getByContactId(contactId);
        return emails.stream()
                .map(emailMapper::mapEmailToDto)
                .collect(Collectors.toList());
    }

}
