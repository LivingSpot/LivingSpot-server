package com.ssafy.living_spot.auth.jwt.component;

import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.REFRESH_TOKEN_PREFIX;

import com.ssafy.living_spot.auth.jwt.dto.response.JwtToken;
import com.ssafy.living_spot.common.util.RedisUtil;
import com.ssafy.living_spot.member.domain.Member;
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

    public JwtToken generateTokens(Member member) {
        String accessToken = generateAccessToken(member);
        String refreshToken = generateRefreshToken(member);

        storeRefreshToken(member.getId(), refreshToken);

        return new JwtToken(accessToken, refreshToken);
    }

    private String generateAccessToken(Member member) {
        return Jwts.builder()
                .claim("id", member.getId())
                .claim("role", member.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                .signWith(jwtSecretKey.getSecretKey())
                .compact();
    }

    private String generateRefreshToken(Member member) {
        return Jwts.builder()
                .claim("id", member.getId())
                .claim("role", member.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration()))
                .signWith(jwtSecretKey.getSecretKey())
                .compact();
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtProperties.getRefreshTokenExpiration() / 1000)
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie deleteRefreshTokenCookie() {
        return ResponseCookie.from("refresh_token", "")
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

    public String getUid(String token) {
        return Jwts.parser().verifyWith(jwtSecretKey.getSecretKey()).build().parseSignedClaims(token).getPayload()
                .get("id", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(jwtSecretKey.getSecretKey()).build().parseSignedClaims(token).getPayload()
                .get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(jwtSecretKey.getSecretKey()).build().parseSignedClaims(token).getPayload()
                .getExpiration().before(new Date());
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
