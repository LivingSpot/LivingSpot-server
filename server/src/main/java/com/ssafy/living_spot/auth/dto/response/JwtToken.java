package com.ssafy.living_spot.auth.dto.response;

public record JwtToken(
        String accessToken,
        String refreshToken
) {
}
