package com.sermaluc.user.register.service;

import com.sermaluc.user.register.model.dto.PhoneDTO;
import java.util.List;
import java.util.Optional;

public interface PhoneService {
    List<PhoneDTO> findAll();
    Optional<PhoneDTO> findById(Long id);
    PhoneDTO save(PhoneDTO phone, Long userId);
    void deleteById(Long id);
}