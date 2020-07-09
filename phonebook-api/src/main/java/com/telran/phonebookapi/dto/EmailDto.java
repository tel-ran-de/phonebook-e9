package com.telran.phonebookapi.dto;
import com.telran.phonebookapi.entity.EmailType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    public int id;
    public String email;
    public EmailType type;
    public int contactId;

}
