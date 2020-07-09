package com.telran.phonebookapi.dto;

import com.telran.phonebookapi.entity.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    public int id;
    public String name;
    public String lastName;
    public ContactType type;
    public String userId;
    
    public List<PhoneDto> phones = new ArrayList<>();

    public List<AddressDto> addresses = new ArrayList<>();

    public List<EmailDto> emails = new ArrayList<>();
}
