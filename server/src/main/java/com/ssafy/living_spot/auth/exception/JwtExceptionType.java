package com.ssafy.living_spot.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtExceptionType {
    /*
    Token 관련 Exception
     */
    INVALID_JWT_SIGNATURE("유효하지 않은 Signature 입니다."),
    INVALID_JWT_TOKEN("유효하지 않은 Token 입니다."),
    EXPIRED_JWT_TOKEN("만료된 Token 입니다."),
    UNSUPPORTED_JWT_TOKEN("지원하지 않는 형식의 Token 입니다."),
    EMPTY_JWT("Jwt 토큰이 비어있습니다."),

    /*
    자격 증명 관련 Exception
     */
    FAILURE_AUTHENTICATION("자격 증명에 실패하였습니다."),

    INVALID_EMAIL_OR_PASSWORD("이메일 또는 비밀번호가 일치하지 않습니다.");

    private final String message;
}
