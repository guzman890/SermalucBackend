package com.sermaluc.user.register.service.mapper;

import com.sermaluc.user.register.model.dto.PhoneDTO;
import com.sermaluc.user.register.model.dto.UserDTO;
import com.sermaluc.user.register.model.entity.PhoneEntity;
import com.sermaluc.user.register.model.entity.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toDTO(UserEntity user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setCreated(user.getCreated());
        userDTO.setModified(user.getModified());
        userDTO.setStatus(user.getStatus());

        if ( user.getPhones() != null)
            userDTO.setPhones(user.getPhones().stream()
                    .map(PhoneMapper::toDTO).
                    collect(Collectors.toList())
            );
        return userDTO;
    }

    public static UserEntity toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setName(userDTO.getName());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setCreated(userDTO.getCreated());
        userEntity.setModified(userDTO.getModified());
        userEntity.setStatus(userDTO.getStatus());
        return userEntity;
    }
}