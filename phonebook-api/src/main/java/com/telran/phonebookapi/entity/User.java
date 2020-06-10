package com.telran.phonebookapi.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity(name = "Users")
public class User {

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
public class User {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

    @Id
    private String email;

    @Setter
    private String password;

    @Setter
    private UserRole userRole;

    @Setter
    private Boolean enabled;
    private String name;

    @Setter
    private String lastName;

    @Setter
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts = new ArrayList<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        userRole = UserRole.USER;
        enabled = false;
    }
}
    }

    public List<Contact> getContact() {
        return Collections.unmodifiableList(contacts);
    }
}

