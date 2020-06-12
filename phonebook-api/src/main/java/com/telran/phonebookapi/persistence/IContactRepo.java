package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.Contact;
import com.telran.phonebookapi.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IContactRepo extends CrudRepository<Contact, Integer> {

    List<Contact> findByName(String name);

    List<Contact> getByUser(User user);

}
