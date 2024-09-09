package com.sermaluc.user.register.service.impl;

import com.sermaluc.user.register.exception.UserNotFoundException;
import com.sermaluc.user.register.model.dto.PhoneDTO;
import com.sermaluc.user.register.model.entity.PhoneEntity;
import com.sermaluc.user.register.model.entity.UserEntity;
import com.sermaluc.user.register.repository.PhoneRepository;
import com.sermaluc.user.register.repository.UserRepository;
import com.sermaluc.user.register.service.PhoneService;
import com.sermaluc.user.register.service.mapper.PhoneMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PhoneDTO> findAll() {
        return phoneRepository.findAll().stream()
                .map(PhoneMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PhoneDTO> findById(Long id) {
        return phoneRepository.findById(id)
                .map(PhoneMapper::toDTO);
    }

    @Override
    public PhoneDTO save(PhoneDTO phoneDTO, Long userId) {
        UserEntity currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        PhoneEntity phoneEntity = PhoneMapper.toEntity(phoneDTO);
        phoneEntity.setUser(currentUser);
        return PhoneMapper.toDTO(phoneRepository.save(phoneEntity));
    }

    @Override
    public void deleteById(Long id) {
        phoneRepository.deleteById(id);
    }
}
