package com.sermaluc.user.register.repository;

import com.sermaluc.user.register.model.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
}
