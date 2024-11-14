package com.ssafy.living_spot.auth.jwt.component;

import static com.ssafy.living_spot.auth.jwt.exception.JwtExceptionType.EMPTY_JWT;
import static com.ssafy.living_spot.auth.jwt.exception.JwtExceptionType.EXPIRED_JWT_TOKEN;
import static com.ssafy.living_spot.auth.jwt.exception.JwtExceptionType.INVALID_JWT_SIGNATURE;
import static com.ssafy.living_spot.auth.jwt.exception.JwtExceptionType.INVALID_JWT_TOKEN;
import static com.ssafy.living_spot.auth.jwt.exception.JwtExceptionType.UNSUPPORTED_JWT_TOKEN;

import com.ssafy.living_spot.auth.jwt.exception.JwtAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenValidator {
    private final JwtSecretKey secretKey;

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey.getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new JwtAuthenticationException(INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtAuthenticationException(INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new JwtAuthenticationException(EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new JwtAuthenticationException(UNSUPPORTED_JWT_TOKEN);
        } catch (Exception e) {
            log.error("JWT token validation failed: {}", e.getMessage());
            throw new JwtAuthenticationException(EMPTY_JWT);
        }
    }
}
