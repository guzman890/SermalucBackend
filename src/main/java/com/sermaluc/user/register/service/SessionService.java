package com.sermaluc.user.register.service;

import com.sermaluc.user.register.model.dto.SessionDTO;
import java.util.List;
import java.util.Optional;

public interface SessionService {
    List<SessionDTO> findAll();
    Optional<SessionDTO> findById(Long id);
    SessionDTO save(SessionDTO session);
    SessionDTO create(String username, Long userId);
    void deleteById(Long id);
}