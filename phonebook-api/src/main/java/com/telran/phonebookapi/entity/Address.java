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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    private int zipCode;
    @Setter
    private String city;
    @Setter
    private String country;
    @Setter
    private String address;
    @Setter
    private AddressType type;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contact contact;

    public Address(String address, String country, String city, int zipCode, AddressType type, Contact contact) {
        this.address = address;
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
        this.contact = contact;
        this.type = type;
    }
}
