package com.sermaluc.user.register.model.dto;

import lombok.Data;

@Data
public class SessionDTO {
    private Long id;
    private Long userId;
    private String token;
}
