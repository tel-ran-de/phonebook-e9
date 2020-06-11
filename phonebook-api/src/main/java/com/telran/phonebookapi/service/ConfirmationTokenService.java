package com.telran.phonebookapi.service;

import com.telran.phonebookapi.entity.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    void save(ConfirmationToken confirmationToken);

    void delete(Long id);

    Optional<ConfirmationToken> findByToken(String token);
}
