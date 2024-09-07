package com.sermaluc.user.register.service.impl;

import com.sermaluc.user.register.business.UserValidations;
import com.sermaluc.user.register.model.Status;
import com.sermaluc.user.register.model.dto.PhoneDTO;
import com.sermaluc.user.register.model.dto.SessionDTO;
import com.sermaluc.user.register.model.dto.UserDTO;
import com.sermaluc.user.register.model.request.LoginRequest;
import com.sermaluc.user.register.model.request.LoginResponse;
import com.sermaluc.user.register.model.request.RegisterRequest;
import com.sermaluc.user.register.model.request.RegisterResponse;
import com.sermaluc.user.register.service.AuthService;
import com.sermaluc.user.register.service.PhoneService;
import com.sermaluc.user.register.service.SessionService;
import com.sermaluc.user.register.service.UserService;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private UserValidations userValidations;

    @Override
    public Optional<LoginResponse> login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);

        UserDTO userDTO = userService.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SessionDTO sessionDTO = sessionService.create(loginRequest.getEmail(), userDTO.getId());
        String jwt = sessionDTO.getToken();

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwt)
                .build();
        return  Optional.of(loginResponse);
    }

    @Override
    public Optional<RegisterResponse> register(RegisterRequest request) {

        userValidations.isValidEmail(request.getEmail());

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(request.getEmail());
        userDTO.setName(request.getName());
        userDTO.setPassword(request.getPassword());
        userDTO.setCreated(new Date());
        userDTO.setModified(new Date());
        userDTO.setStatus(Status.ACTIVE);

        UserDTO userDTOSaved = userService.save(userDTO);

        request.getPhones().forEach(phoneRequest -> {
            PhoneDTO phoneDto= new PhoneDTO();
            phoneDto.setNumber(phoneRequest.getNumber());
            phoneDto.setCitycode(phoneRequest.getCitycode());
            phoneDto.setContrycode(phoneRequest.getContrycode());

            phoneService.save(phoneDto, userDTOSaved.getId());
        });

        SessionDTO sessionDTO = sessionService.create(userDTOSaved.getEmail(), userDTOSaved.getId());

        // Create RegisterResponse
        RegisterResponse response = new RegisterResponse();
        response.setCreated(userDTOSaved.getCreated());
        response.setModified(userDTOSaved.getModified());
        response.setId(userDTOSaved.getId());

        response.setToken(sessionDTO.getToken());

        response.setLastLogin(userDTOSaved.getCreated());
        response.setIsactive(userDTOSaved.getStatus());
        return Optional.of(response);
    }
}
