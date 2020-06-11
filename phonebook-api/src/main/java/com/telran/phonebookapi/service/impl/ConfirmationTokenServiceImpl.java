package com.telran.phonebookapi.service.impl;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.repository.ConfirmationTokenRepository;
import com.telran.phonebookapi.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void save(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void delete(Long id){
        confirmationTokenRepository.deleteById(id);
    }

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }
}
