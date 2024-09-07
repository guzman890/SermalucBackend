package com.sermaluc.user.register.service;


import com.sermaluc.user.register.model.request.LoginRequest;
import com.sermaluc.user.register.model.request.LoginResponse;
import com.sermaluc.user.register.model.request.RegisterRequest;
import com.sermaluc.user.register.model.request.RegisterResponse;
import java.util.Optional;


public interface AuthService {
    Optional<LoginResponse> login(LoginRequest loginRequest);
    Optional<RegisterResponse> register(RegisterRequest request);

}
