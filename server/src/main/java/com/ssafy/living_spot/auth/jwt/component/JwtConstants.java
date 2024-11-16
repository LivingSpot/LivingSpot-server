package com.ssafy.living_spot.auth.jwt.component;

public class JwtConstants {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final String REFRESH_TOKEN_PREFIX = "RT:";

    // 인스턴스화 방지
    private JwtConstants() {}
}
