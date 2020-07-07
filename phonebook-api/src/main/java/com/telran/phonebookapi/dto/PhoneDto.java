package com.telran.phonebookapi.dto;
import com.telran.phonebookapi.entity.CountryCode;
import com.telran.phonebookapi.entity.PhoneType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PhoneDto {

    private int id;
    @Setter
    public String number;
    @Setter
    public CountryCode codeCountry;
    @Setter
    public PhoneType type;
    public int contactId;

    public PhoneDto(int id, String number, CountryCode codeCountry, PhoneType type, int contactId) {
        this.id = id;
        this.number = number;
        this.codeCountry = codeCountry;
        this.type = type;
        this.contactId = contactId;
    }

}
