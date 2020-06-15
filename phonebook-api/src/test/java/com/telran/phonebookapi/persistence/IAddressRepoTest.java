package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.Address;
import com.telran.phonebookapi.entity.AddressType;
import com.telran.phonebookapi.entity.Contact;
import com.telran.phonebookapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IAddressRepoTest {

    @Autowired
    IAddressRepo addressRepo;

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

        Address address = new Address(contact, AddressType.HOME);
        Address address2 = new Address(contact, AddressType.WORK);
        Address address3 = new Address(contact2, AddressType.WORK);
        Address address4 = new Address(contact3, AddressType.OTHER);
        entityManager.persist(address);
        entityManager.persist(address2);
        entityManager.persist(address3);
        entityManager.persist(address4);
        entityManager.flush();
        entityManager.clear();

        List<Address> addressesFromDb = addressRepo.getByContactId(1);
        assertEquals(2, addressesFromDb.size());
        assertEquals(AddressType.HOME, addressesFromDb.get(0).getType());
        assertEquals(AddressType.WORK, addressesFromDb.get(1).getType());
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

        Address address = new Address(contact, AddressType.HOME);
        Address address2 = new Address(contact, AddressType.WORK);
        Address address3 = new Address(contact3, AddressType.WORK);
        Address address4 = new Address(contact3, AddressType.OTHER);
        entityManager.persist(address);
        entityManager.persist(address2);
        entityManager.persist(address3);
        entityManager.persist(address4);
        entityManager.flush();
        entityManager.clear();

        List<Address> addressesFromDb = addressRepo.getByContactId(2);
        assertEquals(0, addressesFromDb.size());
    }

    @Test
    public void testFindByZipCode_threeContacts_oneFound(){
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

        Address address = new Address(contact, AddressType.HOME);
        Address address2 = new Address(contact, AddressType.WORK);
        Address address3 = new Address(contact2, AddressType.WORK);
        Address address4 = new Address(contact3, AddressType.OTHER);
        address.setZipCode(11111);
        address2.setZipCode(22222);
        address3.setZipCode(33333);
        address4.setZipCode(44444);
        entityManager.persist(address);
        entityManager.persist(address2);
        entityManager.persist(address3);
        entityManager.persist(address4);
        entityManager.flush();
        entityManager.clear();

        List<Address> addressesFromDb = addressRepo.getByZipCode(22222);
        assertEquals(1, addressesFromDb.size());
        assertEquals(AddressType.WORK, addressesFromDb.get(0).getType());
        assertEquals(22222, addressesFromDb.get(0).getZipCode());
    }

    @Test
    public void testFindByZipCode_threeContacts_noFound(){
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

        Address address = new Address(contact, AddressType.HOME);
        Address address2 = new Address(contact, AddressType.WORK);
        Address address3 = new Address(contact2, AddressType.WORK);
        Address address4 = new Address(contact3, AddressType.OTHER);
        address.setZipCode(11111);
        address2.setZipCode(22222);
        address3.setZipCode(33333);
        address4.setZipCode(44444);
        entityManager.persist(address);
        entityManager.persist(address2);
        entityManager.persist(address3);
        entityManager.persist(address4);
        entityManager.flush();
        entityManager.clear();

        List<Address> addressesFromDb = addressRepo.getByZipCode(12345);
        assertEquals(0, addressesFromDb.size());
    }

    @Test
    public void testFindByCountry_threeContacts_twoFound(){
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

        Address address = new Address(contact, AddressType.HOME);
        Address address2 = new Address(contact, AddressType.WORK);
        Address address3 = new Address(contact2, AddressType.WORK);
        Address address4 = new Address(contact3, AddressType.OTHER);
        address.setCountry("Germany");
        address2.setCountry("USA");
        address3.setCountry("Germany");
        address4.setCountry("Russia");
        entityManager.persist(address);
        entityManager.persist(address2);
        entityManager.persist(address3);
        entityManager.persist(address4);
        entityManager.flush();
        entityManager.clear();

        List<Address> addressesFromDb = addressRepo.getByCountry("Germany");
        assertEquals(2, addressesFromDb.size());
        assertEquals("Vasya", addressesFromDb.get(0).getContact().getName());
        assertEquals("Petya", addressesFromDb.get(1).getContact().getName());
    }

    @Test
    public void testFindByCity_threeContacts_threeFound(){
        User user = new User ("email@email.com", "1234");
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        Contact contact = new Contact("Vasya", "Vasin", user);
        Contact contact2 = new Contact("Petya", "Vasin", user);
        Contact contact3 = new Contact("Stepa", "Petin", user);
        entityManager.persist(contact);
        entityManager.persist(contact2);
        entityManager.persist(contact3);
        entityManager.flush();
        entityManager.clear();

        Address address = new Address(contact, AddressType.HOME);
        Address address2 = new Address(contact, AddressType.WORK);
        Address address3 = new Address(contact2, AddressType.WORK);
        Address address4 = new Address(contact3, AddressType.OTHER);
        address.setCity("Berlin");
        address2.setCity("Berlin");
        address3.setCity("Moscow");
        address4.setCity("Berlin");
        entityManager.persist(address);
        entityManager.persist(address2);
        entityManager.persist(address3);
        entityManager.persist(address4);
        entityManager.flush();
        entityManager.clear();

        List<Address> addressesFromDb = addressRepo.getByCity("Berlin");
        assertEquals(3, addressesFromDb.size());
        assertEquals("Vasya", addressesFromDb.get(0).getContact().getName());
        assertEquals("Vasya", addressesFromDb.get(1).getContact().getName());
        assertEquals("Stepa", addressesFromDb.get(2).getContact().getName());
    }
}
