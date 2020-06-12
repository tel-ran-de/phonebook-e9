package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.Address;
import com.telran.phonebookapi.entity.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAddressRepo extends CrudRepository<Address, Integer> {

    List<Address> getByContact(Contact contact);

    List<Address> findByContactId(int contactId);

    List<Address> findByZipCode(int zipCode);

    List<Address> findByCountry(String country);

    List<Address> findByCity(String city);
}
