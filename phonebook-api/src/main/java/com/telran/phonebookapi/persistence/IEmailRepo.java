package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.Contact;
import com.telran.phonebookapi.entity.Email;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEmailRepo extends CrudRepository<Email, Integer> {

    List<Email> getByContact(Contact contact);

    List<Email> findByContactId(int contactId);
}
