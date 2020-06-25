package com.telran.phonebookapi.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

    private String refreshToken;

    @Setter
    private Boolean isValid;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
