package com.sermaluc.user.register.service.impl;


import com.sermaluc.user.register.exception.EmailAlreadyExistsException;
import com.sermaluc.user.register.model.dto.UserDTO;
import com.sermaluc.user.register.model.entity.UserEntity;
import com.sermaluc.user.register.repository.UserRepository;
import com.sermaluc.user.register.service.UserService;
import com.sermaluc.user.register.service.mapper.UserMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDTO);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists: " + userDTO.getEmail());
        }
        UserEntity userEntity = UserMapper.toEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return UserMapper.toDTO(userRepository.save(userEntity));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}