package com.sermaluc.user.register.business;

import com.sermaluc.user.register.exception.InvalidEmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserValidations {

    @Value("${user.validation.email.regex}")
    private String emailRegex;

    public void isValidEmail(String email) {
        if (email == null || !email.matches(emailRegex)) {
            throw new InvalidEmailException("Invalid email format");
        }
    }
}
