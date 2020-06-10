package com.telran.phonebookapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    private String number;
    @Setter
    private CountryCode codeCountry;
    @Setter
    private PhoneType type;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contact contact;

    public Phone(Contact contact, CountryCode codeCountry, String number, PhoneType type) {
        this.contact = contact;
        this.codeCountry = codeCountry;
        this.number = number;
        this.type = type;
    }
}
