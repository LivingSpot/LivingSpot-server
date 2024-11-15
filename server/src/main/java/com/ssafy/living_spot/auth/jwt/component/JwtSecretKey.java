package com.ssafy.living_spot.auth.jwt.component;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtSecretKey {
    private SecretKey secretKey;
    private final JwtProperties jwtProperties;

    @PostConstruct
    void init() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        secretKey = new SecretKeySpec(keyBytes, Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}
