package com.ssafy.living_spot.auth.jwt.exception;

import com.ssafy.living_spot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends BaseException {
    public JwtAuthenticationException(JwtExceptionType errorMessage) {
        super(HttpStatus.UNAUTHORIZED, errorMessage);
    }
}
