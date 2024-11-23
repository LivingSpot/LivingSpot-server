package com.ssafy.living_spot.auth.filter;

import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.AUTHORIZATION_HEADER;
import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.BEARER_PREFIX;
import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.REFRESH_TOKEN_COOKIE_NAME;

import com.ssafy.living_spot.auth.jwt.component.JwtTokenValidator;
import com.ssafy.living_spot.auth.jwt.component.JwtUtil;
import com.ssafy.living_spot.auth.dto.MemberTokenInfo;
import com.ssafy.living_spot.auth.dto.response.JwtToken;
import com.ssafy.living_spot.auth.exception.CustomAuthenticationEntryPoint;
import com.ssafy.living_spot.common.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenValidator jwtTokenValidator;
    private final JwtUtil jwtUtil;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private static final List<RequestMatcher> LOGIN_PATHS = Arrays.asList(
            new AntPathRequestMatcher("/auth/login", HttpMethod.POST.toString()),
            new AntPathRequestMatcher("/member/signup", HttpMethod.POST.toString()),
            new AntPathRequestMatcher("/member/upload-profile-image", HttpMethod.POST.toString()),
            new AntPathRequestMatcher("/profile/**", HttpMethod.GET.toString())
    );

    private static final RequestMatcher EXCLUDED_PATHS_REQUEST_MATCHER = new OrRequestMatcher(LOGIN_PATHS);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if(shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String accessToken = extractAccessToken(request);
            log.info("Access Token: {}", accessToken);
            if (accessToken != null) {
                jwtTokenValidator.validateToken(accessToken);
                // Access Token이 만료되었는지 확인
                if (!jwtUtil.isExpired(accessToken)) {
                    //Access Token이 유효한 경우
                    processValidAccessToken(accessToken);
                } else {
                    //Access Token이 만료된 경우
                    processExpiredAccessToken(request, response);
                }
            }
        } catch (AuthenticationException e) {
            customAuthenticationEntryPoint.commence(request, response, e);
            SecurityContextHolder.clearContext();
            response.addHeader("set-Cookie", CookieUtil.deleteRefreshTokenCookie().toString());
        }

        filterChain.doFilter(request, response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        boolean shouldNotFilter = EXCLUDED_PATHS_REQUEST_MATCHER.matches(request);
        log.info("Should not filter for request [{}]: {}", request.getRequestURI(), shouldNotFilter);
        return shouldNotFilter;
    }

    private String extractAccessToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void processValidAccessToken(String accessToken) {
        Long memberId = jwtUtil.getMemberId(accessToken);
        String role = jwtUtil.getRole(accessToken);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                memberId,
                null,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void processExpiredAccessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        log.info("Processing request in JWT Filter: " + request.getRequestURI());
        Optional<Cookie> refreshTokenCookie = extractRefreshTokenFromCookie(request);

        if (refreshTokenCookie.isPresent()) {
            String refreshToken = refreshTokenCookie.get().getValue();

            jwtTokenValidator.validateToken(refreshToken);

            // Refresh Token이 만료되지 않았는지 확인
            if (!jwtUtil.isExpired(refreshToken)) {
                Long memberId = jwtUtil.getMemberId(refreshToken);

                // Redis에 저장된 Refresh Token과 일치하는지 확인
                if (jwtUtil.validateRefreshToken(memberId, refreshToken)) {
                    String role = jwtUtil.getRole(refreshToken);

                    // 새로운 토큰 쌍 생성을 위한 임시 Member 객체 생성
                    MemberTokenInfo memberTokenInfo = new MemberTokenInfo(memberId, role);

                    // 새로운 토큰 쌍 생성
                    JwtToken newTokens = jwtUtil.generateTokens(memberTokenInfo);

                    // 새로운 토큰을 응답 헤더에 설정
                    response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + newTokens.accessToken());
                    ResponseCookie newRefreshTokenCookie = CookieUtil.createRefreshTokenCookie(newTokens.refreshToken());
                    response.addHeader("set-cookie", newRefreshTokenCookie.toString());

                    // SecurityContext 설정
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    memberId,
                                    null,
                                    Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role))
                            );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Refresh Token이 유효하지 않은 경우
                    SecurityContextHolder.clearContext();
                    response.addHeader("set-cookie", CookieUtil.deleteRefreshTokenCookie().toString());
                }
            } else {
                // Refresh Token이 만료된 경우
                SecurityContextHolder.clearContext();
                response.addHeader("set-cookie", CookieUtil.deleteRefreshTokenCookie().toString());
            }
        }
    }

    private Optional<Cookie> extractRefreshTokenFromCookie(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(cookie -> REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .findFirst();
    }
}
