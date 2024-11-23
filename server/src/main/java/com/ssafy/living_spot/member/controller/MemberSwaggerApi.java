package com.ssafy.living_spot.member.controller;

import com.ssafy.living_spot.member.dto.request.MemberSignUpRequest;
import com.ssafy.living_spot.member.dto.response.MemberProfileResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface MemberSwaggerApi {

    @ApiResponse(responseCode = "200", description = "회원가입")
    ResponseEntity<?> signUp(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest);

    @ApiResponse(responseCode = "200", description = "프로필 이미지 업로드")
    public ResponseEntity<?> uploadProfileImage(@RequestPart("file") MultipartFile file);

    @ApiResponse(responseCode = "200", description = "회원 프로필 조회")
    ResponseEntity<MemberProfileResponse> getUserProfile();

    @ApiResponse(responseCode = "200", description = "로그아웃")
    public ResponseEntity<Void> logout(HttpServletResponse response);
}
