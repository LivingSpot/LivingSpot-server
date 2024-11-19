package com.ssafy.living_spot.external.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleTokenResponse(
        @JsonProperty("id_token") String idToken
) {
}
