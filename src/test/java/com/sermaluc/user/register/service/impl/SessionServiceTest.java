package com.sermaluc.user.register.service.impl;

import com.sermaluc.user.register.model.dto.SessionDTO;
import com.sermaluc.user.register.model.entity.SessionEntity;
import com.sermaluc.user.register.repository.SessionRepository;
import com.sermaluc.user.register.service.JwtService;
import com.sermaluc.user.register.service.mapper.SessionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SessionServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionServiceImpl sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setToken("token");
        sessionEntity.setUserId(1L);

        when(sessionRepository.findAll()).thenReturn(List.of(sessionEntity));

        List<SessionDTO> sessions = sessionService.findAll();

        assertEquals(1, sessions.size());
        assertEquals(sessionEntity.getToken(), sessions.get(0).getToken());
        assertEquals(sessionEntity.getUserId(), sessions.get(0).getUserId());

        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long sessionId = 1L;
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setId(sessionId);
        sessionEntity.setToken("token");
        sessionEntity.setUserId(1L);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(sessionEntity));

        Optional<SessionDTO> sessionDTO = sessionService.findById(sessionId);

        assertTrue(sessionDTO.isPresent());
        assertEquals(sessionEntity.getToken(), sessionDTO.get().getToken());
        assertEquals(sessionEntity.getUserId(), sessionDTO.get().getUserId());

        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    void testSave() {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setToken("token");
        sessionDTO.setUserId(1L);

        SessionEntity sessionEntity = SessionMapper.toEntity(sessionDTO);

        when(sessionRepository.save(any(SessionEntity.class))).thenReturn(sessionEntity);

        SessionDTO savedSession = sessionService.save(sessionDTO);

        assertEquals(sessionDTO.getToken(), savedSession.getToken());
        assertEquals(sessionDTO.getUserId(), savedSession.getUserId());

        verify(sessionRepository, times(1)).save(any(SessionEntity.class));
    }

    @Test
    void testCreate() {
        String username = "user";
        Long userId = 1L;
        String token = "token";

        when(jwtService.create(username)).thenReturn(token);

        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setToken(token);
        sessionDTO.setUserId(userId);

        SessionEntity sessionEntity = SessionMapper.toEntity(sessionDTO);

        when(sessionRepository.save(any(SessionEntity.class))).thenReturn(sessionEntity);

        SessionDTO createdSession = sessionService.create(username, userId);

        assertEquals(token, createdSession.getToken());
        assertEquals(userId, createdSession.getUserId());

        verify(jwtService, times(1)).create(username);
        verify(sessionRepository, times(1)).save(any(SessionEntity.class));
    }

    @Test
    void testDeleteById() {
        Long sessionId = 1L;

        doNothing().when(sessionRepository).deleteById(sessionId);

        sessionService.deleteById(sessionId);

        verify(sessionRepository, times(1)).deleteById(sessionId);
    }
}