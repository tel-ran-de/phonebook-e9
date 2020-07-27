package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.entity.Contact;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.exception.UserNotFoundException;
import com.telran.phonebookapi.persistence.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
public class ContactService {

    public static final String CONTACT_NOT_FOUND = "This contact doesn't exist";
    private static final String USER_NOT_FOUND = "This user doesn't exist";
    final IContactRepo contactRepo;
    final IAddressRepo addressRepo;
    final IPhoneRepo iPhoneRepo;
    final IEmailRepo emailRepo;
    final IUserRepository userRepo;

    public ContactService(IContactRepo contactRepo, IAddressRepo addressRepo, IPhoneRepo iPhoneRepo, IEmailRepo emailRepo, IUserRepository userRepo) {
        this.contactRepo = contactRepo;
        this.addressRepo = addressRepo;
        this.iPhoneRepo = iPhoneRepo;
        this.emailRepo = emailRepo;
        this.userRepo = userRepo;
    }

    public void addContact(ContactDto contactDto){
        User user = userRepo.findById(contactDto.userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        Contact contact = new Contact(contactDto.name, contactDto.lastName, user);
        contact.setType(contactDto.type);
        contactRepo.save(contact);
    }

    public ContactDto getById(int id){
        Contact contact = contactRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_NOT_FOUND));
        ContactDto contactDto = new ContactDto(contact.getId(), contact.getName(), contact.getLastName(),
                contact.getType(), contact.getUser().getEmail());

        return contactDto;
    }
    public ContactDto getByIdFullDetails(int id){
        Contact contact = contactRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_NOT_FOUND));
        ContactDto contactDto = new ContactDto(contact.getId(), contact.getName(), contact.getLastName(),
                contact.getType(), contact.getUser().getEmail());

        contactDto.addresses = contact.getAddresses().stream()
                .map(address -> new AddressDto(address.getId(), address.getZipCode(), address.getCity(),
                        address.getCountry(), address.getAddress(), address.getType(),  address.getContact().getId()))
                .collect(Collectors.toList());

        contactDto.emails = contact.getEmails().stream()
                .map(email -> new EmailDto(email.getId(), email.getEmail(), email.getType(), email.getContact().getId()))
                .collect(Collectors.toList());

        contactDto.phones = contact.getPhoneNumbers().stream()
                .map(phone -> new PhoneDto(phone.getId(), phone.getNumber(), phone.getCodeCountry(), phone.getType(), phone.getContact().getId()))
                .collect(Collectors.toList());

        return contactDto;
    }

    public void edit(ContactDto contactDto){
        Contact contact = contactRepo.findById(contactDto.id).orElseThrow(() -> new EntityNotFoundException(CONTACT_NOT_FOUND));
        contact.setName(contactDto.name);
        contact.setLastName(contactDto.lastName);
        contact.setType(contactDto.type);
        contactRepo.save(contact);
    }

    public void removeById(int id){
        contactRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_NOT_FOUND));
        contactRepo.deleteById(id);
    }
}
