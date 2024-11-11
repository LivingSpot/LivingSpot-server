package com.ssafy.living_spot.common.advice;

import com.ssafy.living_spot.common.Exception.BaseException;
import com.ssafy.living_spot.common.Exception.FailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<FailResponse> handleGlobalException(BaseException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(FailResponse.fail(ex.getStatus().value(), ex.getErrorMessage().getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailResponse> handleDefaultException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(FailResponse.fail(HttpStatus.BAD_REQUEST.value(), ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }
}
