package com.ssafy.living_spot.member.controller;

import com.ssafy.living_spot.common.exception.ErrorMessage;
import com.ssafy.living_spot.common.exception.UnAuthorizedException;
import com.ssafy.living_spot.member.domain.Member;
import com.ssafy.living_spot.member.domain.PrincipalDetail;
import com.ssafy.living_spot.member.dto.request.MemberSignUpRequest;
import com.ssafy.living_spot.member.dto.response.MemberProfileResponse;
import com.ssafy.living_spot.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            throw new UnAuthorizedException(ErrorMessage.NOT_AUTHORIZED_MEMBER);
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof Long)) {
            throw new RuntimeException(ErrorMessage.INVALID_PRINCIPAL_TYPE.getMessage());
        }

        Long memberId = (Long) principal;

        return ResponseEntity.ok(memberService.getMemberProfile(memberId));
    }

}
