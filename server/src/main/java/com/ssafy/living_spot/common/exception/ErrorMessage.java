package com.ssafy.living_spot.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    ALREADY_EXIST_EMAIL("이미 존재하는 이메일입니다."),
    NOT_EXIST_EMAIL("존재하지 않는 이메일입니다."),
    INVALID_REQUEST_FORMAT("요청 형식이 올바르지 않습니다."),
    UNSUPPORTED_PROVIDER("지원하지 않는 플랫폼입니다.");
    private final String message;
}
