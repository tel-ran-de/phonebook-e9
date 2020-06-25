package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.RecoveryPasswordToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IRecoveryPasswordTokenRepo extends CrudRepository<RecoveryPasswordToken, Long> {
    Optional<RecoveryPasswordToken> findByToken(String token);
    Optional<RecoveryPasswordToken> findByUserEmailIgnoreCase(String email);
}
