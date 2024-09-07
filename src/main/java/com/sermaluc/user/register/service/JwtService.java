package com.sermaluc.user.register.service;

public interface JwtService {
    String create(String username);
    String getEmail(String token);
    boolean isValid(String token);
}
