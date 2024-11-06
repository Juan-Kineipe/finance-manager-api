package com.kineipe.financemanager.controller;

import com.kineipe.financemanager.domain.dto.AccountCredentials;
import com.kineipe.financemanager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentials credentials) {
        if (checkParams(credentials))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authService.signin(credentials);
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return token;
    }

    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authService.refreshToken(username, refreshToken);
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return token;
    }

    private boolean checkParams(AccountCredentials credentials) {
        return credentials == null || credentials.getUsername() == null || credentials.getUsername().isBlank() || credentials.getPassword() == null || credentials.getPassword().isBlank();
    }

}
