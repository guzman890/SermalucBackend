package com.sermaluc.user.register.service.mapper;

import com.sermaluc.user.register.model.dto.SessionDTO;
import com.sermaluc.user.register.model.entity.SessionEntity;

public class SessionMapper {
    public static SessionDTO toDTO(SessionEntity session) {
        if (session == null) {
            return null;
        }

        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setId(session.getId());
        sessionDTO.setUserId(session.getUser().getId());
        sessionDTO.setToken(session.getToken());

        return sessionDTO;
    }

    public static SessionEntity toEntity(SessionDTO sessionDTO) {
        if (sessionDTO == null) {
            return null;
        }

        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setId(sessionDTO.getId());
        sessionEntity.setToken(sessionDTO.getToken());

        return sessionEntity;
    }
}