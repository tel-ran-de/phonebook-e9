package com.telran.phonebookapi.repository;

import com.telran.phonebookapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

    @DataJpaTest
    class UserRepositoryTest {

        @Autowired
        UserRepository userRepository;

        @Autowired
        TestEntityManager entityManager;

        @Test
        @Transactional
        public void testFindByEmail_oneUserInDb_oneFound() {
            User user = new User("anna@gmail.com", "sdfafdfdfrdf");

            entityManager.persist(user);
            entityManager.flush();

            User userFromDb = userRepository.findByEmail("anna@gmail.com").get();
            assertEquals("anna@gmail.com", userFromDb.getEmail());
        }

        @Test
        @Transactional
        public void testFindByEmail_oneUserInDb_notFound() {
            User user = new User("anna@gmail.com", "sdfafdfdfrdf");

            entityManager.persist(user);
            entityManager.flush();

            Optional<User> userFromDb = userRepository.findByEmail("john@gmail.com");
            assertEquals(Optional.empty(), userFromDb);
        }
}
