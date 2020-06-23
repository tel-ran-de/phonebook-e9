package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.RecoveryPasswordToken;
import com.telran.phonebookapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class IRecoveryPasswordTokenRepoTest {

    @Autowired
    IRecoveryPasswordTokenRepo recoveryPasswordTokenRepo;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testFindByEmail_oneTokenInDb_notFound() {
        User user = new User("anna@gmail.com", "sdfafdfdfrdf");
        RecoveryPasswordToken token = new RecoveryPasswordToken(user);

        entityManager.persist(user);
        entityManager.persist(token);
        entityManager.flush();
        entityManager.clear();

        Optional<RecoveryPasswordToken> tokenFromDb = recoveryPasswordTokenRepo.findByUserEmailIgnoreCase("notfound@email.com");
        assertEquals(Optional.empty(), tokenFromDb);
    }

    @Test
    public void testFindByEmail_oneTokenInDb_oneFound() {
        User user = new User("anna@gmail.com", "sdfafdfdfrdf");
        RecoveryPasswordToken token = new RecoveryPasswordToken(user);

        entityManager.persist(user);
        entityManager.persist(token);
        entityManager.flush();
        entityManager.clear();

        Optional<RecoveryPasswordToken> tokenFromDb = recoveryPasswordTokenRepo.findByUserEmailIgnoreCase("anna@gmail.com");
        assertEquals(token.getToken(), tokenFromDb.get().getToken());
    }

    @Test
    public void testFindByEmailDifferentCase_oneTokenInDb_oneFound() {
        User user = new User("ANNA@gMail.coM", "sdfafdfdfrdf");
        RecoveryPasswordToken token = new RecoveryPasswordToken(user);

        entityManager.persist(user);
        entityManager.persist(token);
        entityManager.flush();
        entityManager.clear();

        Optional<RecoveryPasswordToken> tokenFromDb = recoveryPasswordTokenRepo.findByUserEmailIgnoreCase("anna@gmAIL.COm");
        assertEquals(token.getToken(), tokenFromDb.get().getToken());
    }
}
