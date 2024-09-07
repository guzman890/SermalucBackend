package com.sermaluc.user.register.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sermaluc.user.register.service.JwtService;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;


@Service
public class JwtServiceImpl implements JwtService {

    private static String SECRET_KEY = "sermaluc";
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    @Override
    public String create(String username) {

        String jwt =JWT.create()
                .withSubject(username)
                .withIssuer("sermaluc")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
                .sign(ALGORITHM);

        return jwt;
    }

    @Override
    public boolean isValid(String jwt) {
        try {
            JWT.require(ALGORITHM)
                    .build()
                    .verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    @Override
    public String getEmail(String jwt) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(jwt)
                .getSubject();
    }
}
