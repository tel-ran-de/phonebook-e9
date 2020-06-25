package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    UserSession findByAccessTokenAndIsValidTrue(String sessionId);
}
