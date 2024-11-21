package com.ssafy.living_spot.auth.controller;

import com.ssafy.living_spot.auth.dto.request.GeneralLoginRequest;
import com.ssafy.living_spot.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApi implements AuthSwaggerApi {
    private final AuthService authService;

    @Override
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> login(
            @Valid @RequestBody GeneralLoginRequest generalLoginRequest,
            HttpServletResponse response
    ) {
        authService.loginWithCredentials(generalLoginRequest, response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @PostMapping("/oauth2-jwt-header")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> extractOAuth2AccessTokenFromCookie(HttpServletRequest request, HttpServletResponse response) {
        authService.extractOAuth2AccessTokenFromCookie(request, response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
