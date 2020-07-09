package com.telran.phonebookapi.dto;
import com.telran.phonebookapi.entity.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private int id;
    public int zipCode;
    public String city;
    public String country;
    public String address;
    public AddressType type;
    public int contactId;

}
