package com.sermaluc.user.register.service.impl;

import com.sermaluc.user.register.exception.UserNotFoundException;
import com.sermaluc.user.register.model.dto.SessionDTO;
import com.sermaluc.user.register.model.entity.SessionEntity;
import com.sermaluc.user.register.model.entity.UserEntity;
import com.sermaluc.user.register.repository.SessionRepository;
import com.sermaluc.user.register.repository.UserRepository;
import com.sermaluc.user.register.service.JwtService;
import com.sermaluc.user.register.service.SessionService;
import com.sermaluc.user.register.service.mapper.SessionMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<SessionDTO> findAll() {
        return sessionRepository.findAll().stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SessionDTO> findById(Long id) {
        return sessionRepository.findById(id)
                .map(SessionMapper::toDTO);
    }

    @Override
    public SessionDTO save(SessionDTO sessionDTO) {
        SessionEntity sessionEntity = SessionMapper.toEntity(sessionDTO);
        UserEntity currentUser = userRepository.findById(sessionDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        sessionEntity.setUser(currentUser);
        return SessionMapper.toDTO(sessionRepository.save(sessionEntity));
    }

    @Override
    public SessionDTO create(String username, Long userId) {

        String jwt = jwtService.create(username);

        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setToken(jwt);
        sessionDTO.setUserId(userId);
        sessionDTO = this.save(sessionDTO);

        return sessionDTO;
    }

    @Override
    public void deleteById(Long id) {
        sessionRepository.deleteById(id);
    }
}