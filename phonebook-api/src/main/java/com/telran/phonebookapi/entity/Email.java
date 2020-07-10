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
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    private String email;
    @Setter
    private EmailType type;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contact contact;

    public Email(Contact contact, String email) {
        this.contact = contact;
        this.email = email;
    }
}
