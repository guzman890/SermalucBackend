package com.sermaluc.user.register.service.impl;

import com.sermaluc.user.register.model.dto.SessionDTO;
import com.sermaluc.user.register.model.entity.SessionEntity;
import com.sermaluc.user.register.model.entity.UserEntity;
import com.sermaluc.user.register.repository.SessionRepository;
import com.sermaluc.user.register.repository.UserRepository;
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

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionServiceImpl sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        UserEntity userEntity= new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("test@example.com");
        userEntity.setName("Test User");

        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setToken("token");
        sessionEntity.setUser(userEntity);

        when(sessionRepository.findAll()).thenReturn(List.of(sessionEntity));

        List<SessionDTO> sessions = sessionService.findAll();

        assertEquals(1, sessions.size());
        assertEquals(sessionEntity.getToken(), sessions.get(0).getToken());
        assertEquals(sessionEntity.getUser().getId(), sessions.get(0).getUserId());

        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long sessionId = 1L;

        UserEntity userEntity= new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("test@example.com");
        userEntity.setName("Test User");

        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setId(sessionId);
        sessionEntity.setToken("token");
        sessionEntity.setUser(userEntity);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(sessionEntity));

        Optional<SessionDTO> sessionDTO = sessionService.findById(sessionId);

        assertTrue(sessionDTO.isPresent());
        assertEquals(sessionEntity.getToken(), sessionDTO.get().getToken());
        assertEquals(sessionEntity.getUser().getId(), sessionDTO.get().getUserId());

        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    void testSave() {
        UserEntity userEntity= new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("test@example.com");
        userEntity.setName("Test User");

        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setToken("token");
        sessionDTO.setUserId(1L);

        SessionEntity sessionEntity = SessionMapper.toEntity(sessionDTO);
        sessionEntity.setUser(userEntity);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
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

        UserEntity userEntity= new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("test@example.com");
        userEntity.setName("Test User");

        when(jwtService.create(username)).thenReturn(token);

        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setToken(token);
        sessionDTO.setUserId(userId);

        SessionEntity sessionEntity = SessionMapper.toEntity(sessionDTO);
        sessionEntity.setUser(userEntity);

        when(sessionRepository.save(any(SessionEntity.class))).thenReturn(sessionEntity);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

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