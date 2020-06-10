package com.telran.phonebookapi.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity(name = "Users")
public class User {

    @Id
    private String email;

    @Setter
    private String password;

    @Setter
    private UserRole userRole;

    @Setter
    private Boolean enabled;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        userRole = UserRole.USER;
        enabled = false;
    }
}
