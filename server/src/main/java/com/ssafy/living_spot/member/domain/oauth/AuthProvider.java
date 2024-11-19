package com.ssafy.living_spot.member.domain.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthProvider {
    LOCAL,
    GOOGLE,
    KAKAO,
    NAVER
}
