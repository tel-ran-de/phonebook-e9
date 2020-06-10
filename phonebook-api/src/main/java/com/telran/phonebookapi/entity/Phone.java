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

    private Enum<CountryCode> codeCountry;
    private Enum<Type> type;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contact contact;

    public Phone(Contact contact, Enum<CountryCode> codeCountry, String number, Enum<Type> type) {
        this.contact = contact;
        this.codeCountry = codeCountry;
        this.number = number;
        this.type = type;
    }
}
