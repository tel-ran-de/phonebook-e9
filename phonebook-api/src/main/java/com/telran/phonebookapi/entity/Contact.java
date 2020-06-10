package com.telran.phonebookapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    private String name;

    @Setter
    private String lastName;

    private Enum<Type> type;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "contact")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Phone> phoneNumbers = new ArrayList<>();

    @OneToMany(mappedBy = "contact")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "contact")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Email> emails = new ArrayList<>();

    public Contact(String name, String lastName, User user) {
        this.name = name;
        this.lastName = lastName;
        this.user = user;
    }

    public List<Phone> getPhoneNumbers() {
        return Collections.unmodifiableList(phoneNumbers);
    }

    public List<Address> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }

    public List<Email> getEmails() {
        return Collections.unmodifiableList(emails);
    }

}
