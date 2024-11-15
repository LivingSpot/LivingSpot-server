package com.ssafy.living_spot.auth.controller;

import com.ssafy.living_spot.auth.jwt.dto.request.LoginRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthSwaggerApi {

    @ApiResponse(responseCode = "200", description = "로그인 성공")
    ResponseEntity<Void> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response);
}
