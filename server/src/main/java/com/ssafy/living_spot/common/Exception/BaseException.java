package com.ssafy.living_spot.common.Exception;

import com.ssafy.living_spot.auth.jwt.exception.JwtExceptionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException{

    HttpStatus status;
    ErrorMessage errorMessage;

    public BaseException(HttpStatus status, ErrorMessage errorMessage) {
        super(errorMessage.toString());
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public BaseException(HttpStatus status, JwtExceptionType errorMessage) {
        super(errorMessage.toString());
        this.status = status;
        this.errorMessage = ErrorMessage.valueOf(errorMessage.toString());
    }
}
