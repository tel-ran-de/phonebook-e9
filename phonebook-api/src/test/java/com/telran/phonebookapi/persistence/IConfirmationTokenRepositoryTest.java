package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.ConfirmationToken;
import com.telran.phonebookapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class IConfirmationTokenRepositoryTest {

    @Autowired
    IConfirmationTokenRepository tokenRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testFindByToken_oneTokenInDb_oneFound() {
        User user = new User("anna@gmail.com", "sdfafdfdfrdf");
        ConfirmationToken token = new ConfirmationToken(user, tokenGenerate());

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
        ConfirmationToken token = new ConfirmationToken(user, tokenGenerate());

        entityManager.persist(user);
        entityManager.persist(token);
        entityManager.flush();
        entityManager.clear();

        Optional<ConfirmationToken> tokenFromDb = tokenRepository.findByToken("1500000");
        assertEquals(Optional.empty(), tokenFromDb);
    }

    private String tokenGenerate() {
        return UUID.randomUUID().toString();
    }
}
