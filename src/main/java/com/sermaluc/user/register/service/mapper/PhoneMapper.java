package com.sermaluc.user.register.service.mapper;

import com.sermaluc.user.register.model.dto.PhoneDTO;
import com.sermaluc.user.register.model.entity.PhoneEntity;

public class PhoneMapper {
    public static PhoneDTO toDTO(PhoneEntity phone) {
        if (phone == null) {
            return null;
        }

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber(phone.getNumber());
        phoneDTO.setCitycode(phone.getCityCode());
        phoneDTO.setContrycode(phone.getContryCode());

        return phoneDTO;
    }

    public static PhoneEntity toEntity(PhoneDTO phoneDTO) {
        if (phoneDTO == null) {
            return null;
        }

        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setNumber(phoneDTO.getNumber());
        phoneEntity.setCityCode(phoneDTO.getCitycode());
        phoneEntity.setContryCode(phoneDTO.getContrycode());

        return phoneEntity;
    }
}
