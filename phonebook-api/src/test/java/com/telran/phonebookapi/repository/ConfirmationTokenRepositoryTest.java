package com.telran.phonebookapi.repository;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ConfirmationTokenRepositoryTest {

    @Autowired
    ConfirmationTokenRepository tokenRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testFindByToken_oneTokenInDb_oneFound() {
        User user = new User("anna@gmail.com", "sdfafdfdfrdf");
        ConfirmationToken token = new ConfirmationToken(user);

        entityManager.persist(user);
        entityManager.persist(token);
        entityManager.flush();
        entityManager.clear();

        Optional<ConfirmationToken> tokenFromDb = tokenRepository.findByToken(token.getToken());
        assertEquals(token.getToken(), tokenFromDb.get().getToken());
    }

    @Test
    public void testFindByToken_oneTokenInDb_notFound() {
        User user = new User("anna@gmail.com", "sdfafdfdfrdf");
        ConfirmationToken token = new ConfirmationToken(user);

        entityManager.persist(user);
        entityManager.persist(token);
        entityManager.flush();
        entityManager.clear();

        Optional<ConfirmationToken> tokenFromDb = tokenRepository.findByToken("1500000");
        assertEquals(Optional.empty(), tokenFromDb);
    }

    @Test
    public void testFindByEmail_oneTokenInDb_notFound() {
        User user = new User("anna@gmail.com", "sdfafdfdfrdf");
        ConfirmationToken token = new ConfirmationToken(user);

        entityManager.persist(user);
        entityManager.persist(token);
        entityManager.flush();
        entityManager.clear();

        Optional<ConfirmationToken> tokenFromDb = tokenRepository.findByUserEmailIgnoreCase("notfound@email.com");
        assertEquals(Optional.empty(), tokenFromDb);
    }

    @Test
    public void testFindByEmail_oneTokenInDb_oneFound() {
        User user = new User("anna@gmail.com", "sdfafdfdfrdf");
        ConfirmationToken token = new ConfirmationToken(user);

        entityManager.persist(user);
        entityManager.persist(token);
        entityManager.flush();
        entityManager.clear();

        Optional<ConfirmationToken> tokenFromDb = tokenRepository.findByUserEmailIgnoreCase("anna@gmail.com");
        assertEquals(token.getToken(), tokenFromDb.get().getToken());
    }

    @Test
    public void testFindByEmailDifferentCase_oneTokenInDb_oneFound() {
        User user = new User("ANNA@gMail.coM", "sdfafdfdfrdf");
        ConfirmationToken token = new ConfirmationToken(user);

        entityManager.persist(user);
        entityManager.persist(token);
        entityManager.flush();
        entityManager.clear();

        Optional<ConfirmationToken> tokenFromDb = tokenRepository.findByUserEmailIgnoreCase("anna@gmAIL.COm");
        assertEquals(token.getToken(), tokenFromDb.get().getToken());
    }
}


