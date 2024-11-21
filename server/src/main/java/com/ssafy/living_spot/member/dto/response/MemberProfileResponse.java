package com.ssafy.living_spot.member.dto.response;

import com.ssafy.living_spot.member.domain.Role;

public record MemberProfileResponse(
        String email,
        String name,
        Role role,
        String profileImageUrl
) {
}
