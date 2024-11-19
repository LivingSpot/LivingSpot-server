package com.ssafy.living_spot.auth.service;

import com.ssafy.living_spot.auth.dto.request.GeneralLoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    public void loginWithCredentials(
            GeneralLoginRequest generalLoginRequest,
            HttpServletResponse response
    ) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        generalLoginRequest.email(),
                        generalLoginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
