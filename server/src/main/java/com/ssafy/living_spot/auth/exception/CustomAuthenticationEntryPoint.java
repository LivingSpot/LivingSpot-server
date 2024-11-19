package com.ssafy.living_spot.auth.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.living_spot.common.exception.FailResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        AuthExceptionType exceptionType = determineExceptionType(authException);
        sendErrorResponse(response, HttpStatus.UNAUTHORIZED, exceptionType.getMessage());
    }

    private AuthExceptionType determineExceptionType(AuthenticationException authException) {
        switch (authException.getClass().getSimpleName()) {
            case "BadCredentialsException":
                return AuthExceptionType.INVALID_EMAIL_OR_PASSWORD;
            case "ExpiredJwtException":
                return AuthExceptionType.EXPIRED_JWT_TOKEN;
            case "SignatureException":
                return AuthExceptionType.INVALID_JWT_SIGNATURE;
            case "MalformedJwtException":
                return AuthExceptionType.INVALID_JWT_TOKEN;
            case "UnsupportedJwtException":
                return AuthExceptionType.UNSUPPORTED_JWT_TOKEN;
            default:
                return AuthExceptionType.FAILURE_AUTHENTICATION;
        }
    }

    public void sendErrorResponse(
            HttpServletResponse response,
            HttpStatus status,
            String message
    ) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status.value());

        FailResponse apiResponse = FailResponse.fail(status.value(), message);
        response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
    }
}
