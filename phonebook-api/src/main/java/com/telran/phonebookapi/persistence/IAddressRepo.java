package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAddressRepo extends CrudRepository<Address, Integer> {

    List<Address> getByContactId(int contactId);

    List<Address> getByZipCode(int zipCode);

    List<Address> getByCountry(String country);

    List<Address> getByCity(String city);
}
