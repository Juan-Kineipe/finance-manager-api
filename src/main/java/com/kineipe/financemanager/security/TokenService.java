package com.kineipe.financemanager.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TokenService {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secret;

    public DecodedJWT getDecodedJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("finance-manager")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid or expired token", exception);
        }
    }

    public Long extractUserId(String token) {
        return getDecodedJWT(token).getClaim("userId").asLong();
    }

    public List<String> extractRoles(String token) {
        return getDecodedJWT(token).getClaim("roles").asList(String.class);
    }
}
