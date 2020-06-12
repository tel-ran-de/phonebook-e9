package com.telran.phonebookapi.service;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.exception.TokenNotFoundException;
import com.telran.phonebookapi.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void save(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public void delete(Long id){
        confirmationTokenRepository.deleteById(id);
    }

    public ConfirmationToken findByToken(String token) throws TokenNotFoundException {
        return confirmationTokenRepository.findByToken(token).orElseThrow(() -> new TokenNotFoundException("Token not found"));
    }
}
