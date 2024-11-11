package com.ssafy.living_spot.member.controller;

import com.ssafy.living_spot.member.dto.request.MemberSignUpRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberSwaggerApi {

    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    ResponseEntity<?> signUp(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest);
}
