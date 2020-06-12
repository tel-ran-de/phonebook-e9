package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.Contact;
import com.telran.phonebookapi.entity.Phone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPhoneRepo extends CrudRepository<Phone, Integer> {

    List<Phone> getByContact(Contact contact);

    List<Phone> findByContactId(int contactId);
}
