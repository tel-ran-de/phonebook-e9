package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IPhoneRepoTest {

    @Autowired
    IPhoneRepo phoneRepo;

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

        Phone phone = new Phone(contact, CountryCode.GERMANY, "11111", PhoneType.HOME);
        Phone phone2 = new Phone(contact, CountryCode.GERMANY, "22222", PhoneType.WORK);
        Phone phone3 = new Phone(contact2, CountryCode.GERMANY, "33333", PhoneType.WORK);
        Phone phone4 = new Phone(contact3, CountryCode.GERMANY, "44444", PhoneType.WORK);
        entityManager.persist(phone);
        entityManager.persist(phone2);
        entityManager.persist(phone3);
        entityManager.persist(phone4);
        entityManager.flush();
        entityManager.clear();

        List<Phone> phonesFromBD = phoneRepo.getByContactId(1);
        assertEquals(2, phonesFromBD.size());
        assertEquals("11111", phonesFromBD.get(0).getNumber());
        assertEquals("22222", phonesFromBD.get(1).getNumber());
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

        Phone phone = new Phone(contact, CountryCode.GERMANY, "11111", PhoneType.HOME);
        Phone phone2 = new Phone(contact, CountryCode.GERMANY, "22222", PhoneType.WORK);
        Phone phone3 = new Phone(contact3, CountryCode.GERMANY, "33333", PhoneType.WORK);
        Phone phone4 = new Phone(contact3, CountryCode.GERMANY, "44444", PhoneType.WORK);
        entityManager.persist(phone);
        entityManager.persist(phone2);
        entityManager.persist(phone3);
        entityManager.persist(phone4);
        entityManager.flush();
        entityManager.clear();

        List<Phone> phonesFromBD = phoneRepo.getByContactId(2);
        assertEquals(0, phonesFromBD.size());
    }

}
