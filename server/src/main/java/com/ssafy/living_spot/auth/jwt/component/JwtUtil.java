package com.ssafy.living_spot.auth.jwt.component;

import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.ACCESS_TOKEN_COOKIE_NAME;
import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.REFRESH_TOKEN_COOKIE_NAME;
import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.REFRESH_TOKEN_PREFIX;

import com.ssafy.living_spot.auth.dto.MemberTokenInfo;
import com.ssafy.living_spot.auth.dto.response.JwtToken;
import com.ssafy.living_spot.common.util.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtSecretKey jwtSecretKey;
    private final JwtProperties jwtProperties;
    private final RedisUtil redisUtil;

    public JwtToken generateTokens(MemberTokenInfo memberTokenInfo) {
        String accessToken = generateAccessToken(memberTokenInfo);
        String refreshToken = generateRefreshToken(memberTokenInfo);

        storeRefreshToken(memberTokenInfo.id(), refreshToken);

        return new JwtToken(accessToken, refreshToken);
    }

    private String generateAccessToken(MemberTokenInfo memberTokenInfo) {
        return Jwts.builder()
                .claim("id", memberTokenInfo.id())
                .claim("role", memberTokenInfo.role())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                .signWith(jwtSecretKey.getSecretKey())
                .compact();
    }

    private String generateRefreshToken(MemberTokenInfo memberTokenInfo) {
        return Jwts.builder()
                .claim("id", memberTokenInfo.id())
                .claim("role", memberTokenInfo.role())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration()))
                .signWith(jwtSecretKey.getSecretKey())
                .compact();
    }

    /**
     * 쿠키로 클라이언트에게 넘겨주고, 로컬 스트리지에 저장하고 나면 쿠키에 있는 Access Token은 삭제할 것이기 때문에 짧게 설정 또한, 쿠키에 있는 Access Token에 접근하기 위해서는 js
     * 조작이 필요하기 때문에 httpOnly를 false로 설정한다. 어차피 Access Token은 쿠키에 저장하지 않을 것이기 때문에, httpOnly를 false로 설정해도 보안에 문제가 없다.
     */
    public ResponseCookie createAccessTokenCookie(String accessToken) {
        return ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, accessToken)
                .secure(true)
                .path("/")
                .maxAge(60 * 10)
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtProperties.getRefreshTokenExpiration())
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie deleteAccessTokenCookie() {
        return ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, "")
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie deleteRefreshTokenCookie() {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

    /**
     * JWT 관련 Method
     */

    public String getMemberId(String token) {
        return getClaims(token).get("id", String.class);
    }

    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtSecretKey.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Redis 관련 Method
     */

    // Redis에 Refresh Token 저장
    private void storeRefreshToken(
            Long memberId,
            String refreshToken
    ) {
        String key = REFRESH_TOKEN_PREFIX + memberId; // 접두사를 추가
        redisUtil.delete(key); // 기존 Refresh Token 무효화
        redisUtil.setValueWithExpiration(key, refreshToken, jwtProperties.getRefreshTokenExpiration());
    }

    // Redis에서 Refresh Token을 검증
    public boolean validateRefreshToken(
            Long memberId,
            String refreshToken
    ) {
        String key = REFRESH_TOKEN_PREFIX + memberId;
        String storedToken = redisUtil.getValue(key);
        return storedToken != null && storedToken.equals(refreshToken);
    }
}
