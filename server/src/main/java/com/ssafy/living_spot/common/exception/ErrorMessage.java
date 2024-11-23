package com.ssafy.living_spot.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    ALREADY_EXIST_EMAIL("이미 존재하는 이메일입니다."),
    NOT_EXIST_EMAIL("존재하지 않는 이메일입니다."),
    INVALID_REQUEST_FORMAT("요청 형식이 올바르지 않습니다."),
    UNSUPPORTED_PROVIDER("지원하지 않는 플랫폼입니다."),
    EMPTY_COOKIE("쿠키가 비어있습니다."),
    INVALID_ACCESS_TOKEN("유효하지 않는 Access Token입니다."),

    INVALID_REFRESH_TOKEN("유효하지 않는 Refresh Token입니다."),
    INVALID_PRINCIPAL_TYPE("유효하지 않는 Principal Type입니다."),
    NOT_EXIST_MEMBER("존재하지 않는 회원입니다."),
    NOT_AUTHORIZED_MEMBER("권한이 없는 회원입니다."),
    WRONG_PASSWORD("비밀 번호가 일치하지 않습니다."),
    FILE_UPLOAD_FAILED("파일 업로드에 실패했습니다."),
    NOT_EXIST_HOUSE("존재하지 않는 아파트 정보입니다.");

    private final String message;
}
