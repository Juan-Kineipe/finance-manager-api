package com.kineipe.financemanager.service;

import com.kineipe.financemanager.domain.User;
import com.kineipe.financemanager.domain.dto.AccountCredentials;
import com.kineipe.financemanager.domain.dto.Token;
import com.kineipe.financemanager.repository.UserRepository;
import com.kineipe.financemanager.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity signin(AccountCredentials credentials) {
        try {
            String username = credentials.getUsername();
            String password = credentials.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            User user = userRepository.findByUsername(username);

            Token tokenResponse = new Token();
            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + "not found!");
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

    public ResponseEntity refreshToken(String username, String refreshToken) {
        User user = userRepository.findByUsername(username);

        Token tokenResponse = new Token();
        if (user != null) {
            tokenResponse = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + username + "not found!");
        }
        return ResponseEntity.ok(tokenResponse);

    }

}
