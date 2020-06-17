package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
}

