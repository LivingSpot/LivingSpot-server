package com.ssafy.living_spot.auth.exception;

import com.ssafy.living_spot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends BaseException {
    public JwtAuthenticationException(AuthExceptionType errorMessage) {
        super(HttpStatus.UNAUTHORIZED, errorMessage);
    }
}
