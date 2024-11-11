package com.ssafy.living_spot.common.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    ALREADY_EXIST_EMAIL("이미 존재하는 이메일입니다.");

    private final String message;
}
