package com.ssafy.living_spot.auth.jwt.component;

import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.REFRESH_TOKEN_PREFIX;

import com.ssafy.living_spot.auth.dto.MemberTokenInfo;
import com.ssafy.living_spot.auth.dto.response.JwtToken;
import com.ssafy.living_spot.common.util.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import lombok.RequiredArgsConstructor;
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
     * JWT 관련 Method
     */

    public Long getMemberId(String token) {
        return getClaims(token).get("id", Long.class);
    }

    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public Boolean isExpired(String token) {
        try {
            Date expiration = getClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            // 토큰이 만료되었음을 명시적으로 true로 반환
            return true;
        }
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
