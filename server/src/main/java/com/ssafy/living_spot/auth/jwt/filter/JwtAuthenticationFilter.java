package com.ssafy.living_spot.auth.jwt.filter;

import com.ssafy.living_spot.auth.jwt.component.JwtSecretKey;
import com.ssafy.living_spot.auth.jwt.component.JwtTokenValidator;
import com.ssafy.living_spot.auth.jwt.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenValidator jwtTokenValidator;
    private final JwtSecretKey secretKey;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = extractToken(request);
            if (token != null) {
                jwtTokenValidator.validateToken(token);
                Long memberId = extractMemberId(getClaims(token));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                memberId,
                                null,
                                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + extractRole(getClaims(token))))
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Long extractMemberId(Claims claims) {
        return claims.get("id", Long.class);
    }

    private String extractRole(Claims claims) {
        return claims.get("role", String.class);
    }
}
