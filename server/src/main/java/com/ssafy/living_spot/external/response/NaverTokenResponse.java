package com.ssafy.living_spot.external.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NaverTokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("expires_in")
        Long expiresIn,
        @JsonProperty("error")
        String error,
        @JsonProperty("error_description")
        String errorDescription
) {
}
