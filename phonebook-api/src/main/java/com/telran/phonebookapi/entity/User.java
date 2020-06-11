package com.telran.phonebookapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @Setter
    private String name;

    @Setter
    private String lastName;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts = new ArrayList<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        userRole = UserRole.USER;
        enabled = false;
    }

    public List<Contact> getContact() {
        return Collections.unmodifiableList(contacts);
    }
}
