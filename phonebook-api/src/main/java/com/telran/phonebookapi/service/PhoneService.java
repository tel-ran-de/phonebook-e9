package com.telran.phonebookapi.service;
import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.entity.Contact;
import com.telran.phonebookapi.entity.Phone;
import com.telran.phonebookapi.persistence.IContactRepo;
import com.telran.phonebookapi.persistence.IPhoneRepo;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

@Service
public class PhoneService {

    private static final String CONTACT_NOT_FOUND = "This contact doesn't exist";
    private static final String PHONE_NOT_FOUND = "This phone doesn't exist";
    final IPhoneRepo iPhoneRepo;
    final IContactRepo contactRepo;

    public PhoneService(IPhoneRepo iPhoneRepo, IContactRepo contactRepo) {
        this.iPhoneRepo = iPhoneRepo;
        this.contactRepo = contactRepo;
    }

    public void addPhone(PhoneDto phoneDto){
        Contact contact = contactRepo.findById(phoneDto.contactId).orElseThrow(() -> new EntityNotFoundException(CONTACT_NOT_FOUND));
        Phone phone = new Phone(contact, phoneDto.codeCountry, phoneDto.number, phoneDto.type);
        iPhoneRepo.save(phone);
    }

    public PhoneDto getById(int id){
        Phone phone = iPhoneRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(PHONE_NOT_FOUND));
        PhoneDto phoneDto = new PhoneDto(phone.getId(), phone.getNumber(), phone.getCodeCountry(), phone.getType(), phone.getContact().getId());
        return phoneDto;
    }

    public void edit(PhoneDto phoneDto){
        Phone phone = iPhoneRepo.findById(phoneDto.getId()).orElseThrow(() -> new EntityNotFoundException(PHONE_NOT_FOUND));
        phone.setCodeCountry(phoneDto.codeCountry);
        phone.setNumber(phoneDto.number);
        phone.setType(phoneDto.type);
        iPhoneRepo.save(phone);
    }

    public void removeById(int id){
        iPhoneRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(PHONE_NOT_FOUND));
        iPhoneRepo.deleteById(id);
    }
}
