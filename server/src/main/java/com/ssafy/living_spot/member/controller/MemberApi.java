package com.ssafy.living_spot.member.controller;

import com.ssafy.living_spot.common.util.RedisUtil;
import com.ssafy.living_spot.common.util.SecurityUtil;
import com.ssafy.living_spot.member.dto.request.MemberSignUpRequest;
import com.ssafy.living_spot.member.dto.response.MemberProfileResponse;
import com.ssafy.living_spot.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApi implements MemberSwaggerApi {

    private final MemberService memberService;
    private final RedisUtil redisUtil;

    @Override
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> signUp(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        return ResponseEntity.ok(memberService.signUp(memberSignUpRequest));

    }

    @Override
    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MemberProfileResponse> getUserProfile() {
        Long memberId = SecurityUtil.getAuthenticatedMemberId();
        return ResponseEntity.ok(memberService.getMemberProfile(memberId));
    }

    @Override
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Long memberId = SecurityUtil.getAuthenticatedMemberId();
        memberService.logout(response, memberId);
        return ResponseEntity.ok().build();
    }
}