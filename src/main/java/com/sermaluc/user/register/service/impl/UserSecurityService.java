package com.sermaluc.user.register.service.impl;

import com.sermaluc.user.register.model.entity.UserEntity;
import com.sermaluc.user.register.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found."));

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .build();
    }
}