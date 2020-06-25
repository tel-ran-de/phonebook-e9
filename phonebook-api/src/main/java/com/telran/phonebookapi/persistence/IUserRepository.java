package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<User, String> {
Optional<User> findByEmailAndEnabledIsTrue(String email);
}
