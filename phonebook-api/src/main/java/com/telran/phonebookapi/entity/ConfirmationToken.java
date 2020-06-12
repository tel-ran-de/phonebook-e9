package com.telran.phonebookapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    private LocalDateTime createdDate;

    @OneToOne(targetEntity = User.class)
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }
}

