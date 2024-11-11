package com.ssafy.living_spot.member.dto.response;

public record MemberInfo(
        Long id,
        String email,
        String name,
        String password
) {
}
