package com.sermaluc.user.register.controller;


import com.sermaluc.user.register.model.request.LoginRequest;
import com.sermaluc.user.register.model.request.LoginResponse;
import com.sermaluc.user.register.model.request.RegisterRequest;
import com.sermaluc.user.register.model.request.RegisterResponse;
import com.sermaluc.user.register.service.AuthService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, loginResponse.getToken())
                .body(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> save(@RequestBody RegisterRequest registerRequest) {
        Optional<RegisterResponse> registerResponse =  authService.register(registerRequest);
        return registerResponse.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}