package com.telran.phonebookapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class RecoveryPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    private LocalDateTime createdDate;

    @OneToOne(targetEntity = User.class)
    private User user;

    public RecoveryPasswordToken(User user, String token) {
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.token = token;
    }
}

