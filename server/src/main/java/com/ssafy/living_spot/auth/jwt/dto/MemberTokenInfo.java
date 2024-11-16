package com.ssafy.living_spot.auth.jwt.dto;

public record MemberTokenInfo(
        Long id,
        String role
) {
}
