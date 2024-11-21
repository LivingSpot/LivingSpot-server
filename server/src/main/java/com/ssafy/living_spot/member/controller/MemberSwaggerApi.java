package com.ssafy.living_spot.member.controller;

import com.ssafy.living_spot.member.dto.request.MemberSignUpRequest;
import com.ssafy.living_spot.member.dto.response.MemberProfileResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberSwaggerApi {

    @ApiResponse(responseCode = "200", description = "회원가입")
    ResponseEntity<?> signUp(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest);

    @ApiResponse(responseCode = "200", description = "회원 프로필 조회")
    ResponseEntity<MemberProfileResponse> getUserProfile();
}
