package com.ssafy.living_spot.auth.filter;

import static com.ssafy.living_spot.auth.exception.AuthExceptionType.FAILURE_OAUTH2_AUTHENTICATION;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Slf4j
public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        log.error("OAuth2 로그인 실패: {}", exception.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, FAILURE_OAUTH2_AUTHENTICATION.getMessage());
        response.sendRedirect("http://localhost:5173/login");
    }
}
