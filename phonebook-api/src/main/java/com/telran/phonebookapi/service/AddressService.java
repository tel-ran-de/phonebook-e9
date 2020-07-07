package com.telran.phonebookapi.service;
import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.entity.Address;
import com.telran.phonebookapi.entity.Contact;
import com.telran.phonebookapi.persistence.IAddressRepo;
import com.telran.phonebookapi.persistence.IContactRepo;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

@Service
public class AddressService {

    private static final String CONTACT_NOT_FOUND = "This contact doesn't exist";
    private static final String ADDRESS_NOT_FOUND = "This address doesn't exist";

    final IAddressRepo addressRepo;
    final IContactRepo contactRepo;

    public AddressService(IAddressRepo addressRepo, IContactRepo contactRepo) {
        this.addressRepo = addressRepo;
        this.contactRepo = contactRepo;
    }

    public void addAddress(AddressDto addressDto){
        Contact contact = contactRepo.findById(addressDto.contactId).orElseThrow(() -> new EntityNotFoundException(CONTACT_NOT_FOUND));
        Address address = new Address(addressDto.address, addressDto.country, addressDto.city, addressDto.zipCode, addressDto.type, contact);
        addressRepo.save(address);
    }

    public AddressDto getById(int id){
        Address address = addressRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(ADDRESS_NOT_FOUND));
        AddressDto addressDto = new AddressDto(address.getId(), address.getZipCode(), address.getCity(), address.getCountry(),
                address.getAddress(), address.getType(), address.getContact().getId());
        return addressDto;
    }

    public void edit(AddressDto addressDto){
        Address address = addressRepo.findById(addressDto.getId()).orElseThrow(() -> new EntityNotFoundException(ADDRESS_NOT_FOUND));
        address.setAddress(addressDto.address);
        address.setCity(addressDto.city);
        address.setCountry(addressDto.country);
        address.setZipCode(addressDto.zipCode);
        address.setType(addressDto.type);
        addressRepo.save(address);
    }

    public void removeById(int id){
        addressRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(ADDRESS_NOT_FOUND));
        addressRepo.deleteById(id);
    }
}
