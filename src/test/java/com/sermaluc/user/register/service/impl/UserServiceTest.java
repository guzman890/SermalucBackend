package com.sermaluc.user.register.service.impl;

import com.sermaluc.user.register.exception.EmailAlreadyExistsException;
import com.sermaluc.user.register.model.dto.UserDTO;
import com.sermaluc.user.register.model.entity.UserEntity;
import com.sermaluc.user.register.repository.UserRepository;
import com.sermaluc.user.register.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setName("Test User");

        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        List<UserDTO> users = userService.findAll();

        assertEquals(1, users.size());
        assertEquals(userEntity.getEmail(), users.get(0).getEmail());
        assertEquals(userEntity.getName(), users.get(0).getName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setEmail("test@example.com");
        userEntity.setName("Test User");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        Optional<UserDTO> userDTO = userService.findById(userId);

        assertTrue(userDTO.isPresent());
        assertEquals(userEntity.getEmail(), userDTO.get().getEmail());
        assertEquals(userEntity.getName(), userDTO.get().getName());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setName("Test User");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        Optional<UserDTO> userDTO = userService.findByEmail(email);

        assertTrue(userDTO.isPresent());
        assertEquals(userEntity.getEmail(), userDTO.get().getEmail());
        assertEquals(userEntity.getName(), userDTO.get().getName());

        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testSave() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setName("Test User");
        userDTO.setPassword("password");

        UserEntity userEntity = UserMapper.toEntity(userDTO);
        userEntity.setPassword("encodedPassword");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO savedUser = userService.save(userDTO);

        assertEquals(userDTO.getEmail(), savedUser.getEmail());
        assertEquals(userDTO.getName(), savedUser.getName());
        assertEquals("encodedPassword", savedUser.getPassword());

        verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(passwordEncoder, times(1)).encode(userDTO.getPassword());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testSaveThrowsEmailAlreadyExistsException() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setName("Test User");
        userDTO.setPassword("password");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(new UserEntity()));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.save(userDTO));

        verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testDeleteById() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}