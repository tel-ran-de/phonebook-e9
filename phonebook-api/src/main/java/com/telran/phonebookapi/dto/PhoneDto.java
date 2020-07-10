package com.telran.phonebookapi.dto;
import com.telran.phonebookapi.entity.CountryCode;
import com.telran.phonebookapi.entity.PhoneType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    private int id;
    public String number;
    public CountryCode codeCountry;
    public PhoneType type;
    public int contactId;

}
