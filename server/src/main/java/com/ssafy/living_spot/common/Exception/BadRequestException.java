package com.ssafy.living_spot.common.Exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException{
    public BadRequestException(ErrorMessage errorMessage) {
        super(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
