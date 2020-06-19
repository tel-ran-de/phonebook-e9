package com.telran.phonebookapi.repository;

import com.telran.phonebookapi.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
