package com.telran.phonebookapi.dto;
import com.telran.phonebookapi.entity.EmailType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class EmailDto {

    public int id;
    @Setter
    public String email;
    @Setter
    public EmailType type;
    public int contactId;

    public EmailDto(int id, String email, EmailType type, int contactId) {
        this.id = id;
        this.email = email;
        this.type = type;
        this.contactId = contactId;
    }
}
