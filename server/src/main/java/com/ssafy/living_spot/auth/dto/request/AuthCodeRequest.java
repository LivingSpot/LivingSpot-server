package com.ssafy.living_spot.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthCodeRequest(
        @JsonProperty("authorizationCode") String authorizationCode,
        @JsonProperty("state") String state
){
}
