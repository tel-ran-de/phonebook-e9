package com.telran.phonebookapi.service;
import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.entity.Contact;
import com.telran.phonebookapi.entity.Email;
import com.telran.phonebookapi.persistence.IContactRepo;
import com.telran.phonebookapi.persistence.IEmailRepo;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

@Service
public class EmailService {

    private static final String CONTACT_NOT_FOUND = "This contact doesn't exist";
    private static final String EMAIL_NOT_FOUND = "This email doesn't exist";
    final IEmailRepo emailRepo;
    final IContactRepo contactRepo;

    public EmailService(IEmailRepo emailRepo, IContactRepo contactRepo) {
        this.emailRepo = emailRepo;
        this.contactRepo = contactRepo;
    }

    public void addEmail(EmailDto emailDto){
        Contact contact = contactRepo.findById(emailDto.contactId).orElseThrow(() -> new EntityNotFoundException(CONTACT_NOT_FOUND));
        Email email = new Email(contact, emailDto.email, emailDto.type);
        emailRepo.save(email);
    }

    public EmailDto getById(int id){
        Email email = emailRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(EMAIL_NOT_FOUND));
        EmailDto emailDto = new EmailDto(email.getId(), email.getEmail(), email.getType(), email.getContact().getId());
        return emailDto;
    }

    public void edit(EmailDto emailDto){
        Email email = emailRepo.findById(emailDto.id).orElseThrow(() -> new EntityNotFoundException(EMAIL_NOT_FOUND));
        email.setEmail(emailDto.email);
        email.setType(emailDto.type);
        emailRepo.save(email);
    }

    public void removeById(int id){
        emailRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(EMAIL_NOT_FOUND));
        emailRepo.deleteById(id);
    }

}
