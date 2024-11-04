package com.ssafy.living_spot.common.Exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FailResponse {
    private final int status;
    private final ErrorMessage errorMessage;

    public static FailResponse fail(int status, ErrorMessage errorMessage) {
        return FailResponse.builder()
                .status(status)
                .errorMessage(errorMessage)
                .build();
    }
}
