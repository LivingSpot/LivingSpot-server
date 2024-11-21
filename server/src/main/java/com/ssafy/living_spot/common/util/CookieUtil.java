package com.ssafy.living_spot.common.util;

import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.ACCESS_TOKEN_COOKIE_NAME;
import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.REFRESH_TOKEN_COOKIE_NAME;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

public class CookieUtil {

    /**
     * 쿠키로 클라이언트에게 넘겨주고, 로컬 스트리지에 저장하고 나면 쿠키에 있는 Access Token은 삭제할 것이기 때문에 짧게 설정 또한, 쿠키에 있는 Access Token에 접근하기 위해서는 js
     * 조작이 필요하기 때문에 httpOnly를 false로 설정한다. 어차피 Access Token은 쿠키에 저장하지 않을 것이기 때문에, httpOnly를 false로 설정해도 보안에 문제가 없다.
     */
    public static ResponseCookie createAccessTokenCookie(String accessToken) {
        return ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, accessToken)
                .secure(true)
                .path("/")
                .maxAge(60 * 10)
                .sameSite("Strict")
                .build();
    }

    public static ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60 * 24 * 7) // 7일
                .sameSite("Strict")
                .build();
    }

    public static ResponseCookie deleteAccessTokenCookie() {
        return ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, "")
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

    public static ResponseCookie deleteRefreshTokenCookie() {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }
}
