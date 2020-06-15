package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IEmailRepoTest {

    @Autowired
    IEmailRepo emailRepo;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testFindByContactId_threeContacts_twoFound(){
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

        Email email = new Email(contact, "email@email", EmailType.HOME);
        Email email2 = new Email(contact, "email2@email", EmailType.WORK);
        Email email3 = new Email(contact2, "email3@email", EmailType.HOME);
        Email email4 = new Email(contact3, "email4@email", EmailType.OTHER);
        entityManager.persist(email);
        entityManager.persist(email2);
        entityManager.persist(email3);
        entityManager.persist(email4);
        entityManager.flush();
        entityManager.clear();

        List<Email> emailsFromBD = emailRepo.getByContactId(1);
        assertEquals(2, emailsFromBD.size());
        assertEquals("email@email", emailsFromBD.get(0).getEmail());
        assertEquals(EmailType.WORK, emailsFromBD.get(1).getType());
    }

    @Test
    public void testFindByContactId_threeContacts_noFound(){
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

        Email email = new Email(contact, "email@email", EmailType.HOME);
        Email email2 = new Email(contact, "email2@email", EmailType.WORK);
        Email email3 = new Email(contact3, "email3@email", EmailType.HOME);
        Email email4 = new Email(contact3, "email4@email", EmailType.OTHER);
        entityManager.persist(email);
        entityManager.persist(email2);
        entityManager.persist(email3);
        entityManager.persist(email4);
        entityManager.flush();
        entityManager.clear();

        List<Email> emailsFromBD = emailRepo.getByContactId(2);
        assertEquals(0, emailsFromBD.size());
    }

}
