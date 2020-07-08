package com.telran.phonebookapi.dto;

import com.telran.phonebookapi.entity.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDto {

    public int id;
    public String name;
    public String lastName;
    public ContactType type;
    public String userId;

    public ContactDto(int id, String name, String lastName, ContactType type, String userId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.type = type;
        this.userId = userId;
    }

//    public List<PhoneDto> phones = new ArrayList<>();
//
//    public List<AddressDto> addresses = new ArrayList<>();
//
//    private List<EmailDto> emails = new ArrayList<>();
}
