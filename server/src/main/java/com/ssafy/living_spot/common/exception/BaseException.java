package com.ssafy.living_spot.common.exception;

import com.ssafy.living_spot.auth.exception.AuthExceptionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException{

    HttpStatus status;
    ErrorMessage errorMessage;
    AuthExceptionType authExceptionType;

    public BaseException(HttpStatus status, ErrorMessage errorMessage) {
        super(errorMessage.toString());
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public BaseException(HttpStatus status, AuthExceptionType authExceptionType) {
        super(authExceptionType.toString());
        this.status = status;
        this.authExceptionType = authExceptionType;
    }
}
