package com.ssafy.living_spot.auth.jwt.dto.response;

public record JwtToken(
        String accessToken,
        String refreshToken
) {
}
