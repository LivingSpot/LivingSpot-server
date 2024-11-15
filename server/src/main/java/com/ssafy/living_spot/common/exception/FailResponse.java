package com.ssafy.living_spot.common.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FailResponse {
    private final int status;
    private final String errorMessage;

    public static FailResponse fail(int status, String errorMessage) {
        return FailResponse.builder()
                .status(status)
                .errorMessage(errorMessage)
                .build();
    }
}
