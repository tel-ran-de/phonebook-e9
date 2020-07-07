package com.telran.phonebookapi.dto;
import com.telran.phonebookapi.entity.AddressType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AddressDto {

    private int id;
    @Setter
    public int zipCode;
    @Setter
    public String city;
    @Setter
    public String country;
    @Setter
    public String address;
    @Setter
    public AddressType type;
    public int contactId;

    public AddressDto(int id, int zipCode, String city, String country, String address, AddressType type, int contactId) {
        this.id = id;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.address = address;
        this.type = type;
        this.contactId = contactId;
    }
}
