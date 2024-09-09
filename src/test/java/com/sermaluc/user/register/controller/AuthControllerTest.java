package com.sermaluc.user.register.controller;

import com.sermaluc.user.register.model.request.LoginRequest;
import com.sermaluc.user.register.model.request.LoginResponse;
import com.sermaluc.user.register.model.request.RegisterRequest;
import com.sermaluc.user.register.model.request.RegisterResponse;
import com.sermaluc.user.register.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user");
        loginRequest.setPassword("password");

        LoginResponse loginResponse = LoginResponse.builder().token("token").build();

        when(authService.login(any(LoginRequest.class))).thenReturn(Optional.of(loginResponse));

        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("token", response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(loginResponse, response.getBody());
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");

        RegisterResponse registerResponse = new RegisterResponse();

        when(authService.register(any(RegisterRequest.class))).thenReturn(Optional.of(registerResponse));

        ResponseEntity<RegisterResponse> response = authController.save(registerRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(registerResponse, response.getBody());
    }
}