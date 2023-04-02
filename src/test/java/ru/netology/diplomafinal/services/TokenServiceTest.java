package ru.netology.diplomafinal.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TokenServiceTest {

    @Autowired
    public TokenService tokenService;

    @Test
    void generateToken() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("admin", "admin");
        String token = tokenService.generateToken(authentication);
        assertNotNull(token);
    }
}