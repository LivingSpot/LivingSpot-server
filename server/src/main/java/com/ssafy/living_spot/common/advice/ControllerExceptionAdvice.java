package com.ssafy.living_spot.common.advice;

import com.ssafy.living_spot.common.Exception.BaseException;
import com.ssafy.living_spot.common.Exception.FailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<FailResponse> handleGlobalException(BaseException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(FailResponse.fail(ex.getStatus().value(), ex.getErrorMessage()));
    }
}
