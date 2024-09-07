package com.sermaluc.user.register.service;

import com.sermaluc.user.register.model.dto.UserDTO;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAll();
    Optional<UserDTO> findById(Long id);
    Optional<UserDTO> findByEmail(String email);
    UserDTO save(UserDTO user);
    void deleteById(Long id);

}