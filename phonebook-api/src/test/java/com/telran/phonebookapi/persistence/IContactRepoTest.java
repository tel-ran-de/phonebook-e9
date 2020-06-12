package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.Contact;
import com.telran.phonebookapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.GenerationType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IContactRepoTest {

    @Autowired
    IContactRepo contactRepo;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testFindByName_oneContact_oneFound(){
        User user = new User ("email@email.com", "1234");
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        Contact contact = new Contact("Vasya", "Vasin", user);
        entityManager.persist(contact);
        entityManager.flush();
        entityManager.clear();

        List<Contact> contactsFromBD = contactRepo.findByName("Vasya");
        assertEquals(1, contactsFromBD.size());
        assertEquals("Vasya", contactsFromBD.get(0).getName());
    }

    @Test
    public void testFindByName_threeContacts_twoFound(){
        User user = new User ("email@email.com", "1234");
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        Contact contact = new Contact("Vasya", "Vasin", user);
        Contact contact2 = new Contact("Petya", "Vasin", user);
        Contact contact3 = new Contact("Vasya", "Petin", user);
        entityManager.persist(contact);
        entityManager.persist(contact2);
        entityManager.persist(contact3);
        entityManager.flush();
        entityManager.clear();

        List<Contact> contactsFromBD = contactRepo.findByName("Vasya");
        assertEquals(2, contactsFromBD.size());
        assertEquals("Vasin", contactsFromBD.get(0).getLastName());
        assertEquals("Petin", contactsFromBD.get(1).getLastName());
    }

    @Test
    public void testFindByName_threeContacts_noFound(){
        User user = new User ("email@email.com", "1234");
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        Contact contact = new Contact("Vasya", "Vasin", user);
        Contact contact2 = new Contact("Petya", "Vasin", user);
        Contact contact3 = new Contact("Vasya", "Petin", user);
        entityManager.persist(contact);
        entityManager.persist(contact2);
        entityManager.persist(contact3);
        entityManager.flush();
        entityManager.clear();

        List<Contact> contactsFromBD = contactRepo.findByName("Masha");
        assertEquals(0, contactsFromBD.size());
    }

    @Test
    public void testGetByUser_oneContactOneUser_oneFound(){
        User user = new User ("email@email.com", "1234");
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        Contact contact = new Contact("Vasya", "Vasin", user);
        entityManager.persist(contact);
        entityManager.flush();
        entityManager.clear();

        List<Contact> contactsFromBD = contactRepo.getByUser(user);
        assertEquals(1, contactsFromBD.size());
        assertEquals("Vasya", contactsFromBD.get(0).getName());
    }

    @Test
    public void testGetByUser_threeContactsTwoUsers_twoFound(){
        User user = new User ("email@email.com", "1234");
        User user2 = new User ("email2@email.com", "1234");
        entityManager.persist(user);
        entityManager.persist(user2);
        entityManager.flush();
        entityManager.clear();

        Contact contact = new Contact("Vasya", "Vasin", user2);
        Contact contact2 = new Contact("Petya", "Vasin", user2);
        Contact contact3 = new Contact("Stepa", "Vasin", user);
        entityManager.persist(contact);
        entityManager.persist(contact2);
        entityManager.persist(contact3);
        entityManager.flush();
        entityManager.clear();

        List<Contact> contactsFromBD = contactRepo.getByUser(user2);
        assertEquals(2, contactsFromBD.size());
        assertEquals("Vasya", contactsFromBD.get(0).getName());
        assertEquals("Petya", contactsFromBD.get(1).getName());
    }

}
