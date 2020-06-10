package com.telran.phonebookapi.service;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import com.telran.phonebookapi.exception.UserAlreadyExistsException;

public interface UserService {
    void create(User user) throws UserAlreadyExistsException;
    void confirmUser(ConfirmationToken confirmationToken);
}
