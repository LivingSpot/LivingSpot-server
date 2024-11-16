package com.ssafy.living_spot.auth.jwt.filter;

import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.AUTHORIZATION_HEADER;
import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.BEARER_PREFIX;
import static com.ssafy.living_spot.common.exception.ErrorMessage.INVALID_REQUEST_FORMAT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.living_spot.auth.jwt.component.JwtUtil;
import com.ssafy.living_spot.auth.jwt.dto.MemberTokenInfo;
import com.ssafy.living_spot.auth.jwt.dto.request.LoginRequest;
import com.ssafy.living_spot.auth.jwt.dto.response.JwtToken;
import com.ssafy.living_spot.auth.jwt.exception.CustomAuthenticationEntryPoint;
import com.ssafy.living_spot.common.exception.BadRequestException;
import com.ssafy.living_spot.member.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public CustomLoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                             CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {

        LoginRequest loginRequest;
        try {
            loginRequest = objectMapper.readValue(
                    request.getInputStream(),
                    LoginRequest.class
            );
        } catch (IOException e) {
            throw new BadRequestException(INVALID_REQUEST_FORMAT);
        }

        log.info("Attempting authentication for user: {}", loginRequest.email());

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        MemberTokenInfo memberTokenInfo = new MemberTokenInfo(member.getId(), member.getRole().toString());
        JwtToken tokenResponse = jwtUtil.generateTokens(memberTokenInfo);

        ResponseCookie refreshTokenCookie = jwtUtil.createRefreshTokenCookie(tokenResponse.refreshToken());
        log.info("refreshTokenCookie: {}", refreshTokenCookie);
        response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + tokenResponse.accessToken());
        response.addHeader("set-cookie", refreshTokenCookie.toString());
        log.info(response.getHeader("set-cookie"));
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException {
        ResponseCookie deletedRefreshTokenCookie = jwtUtil.deleteRefreshTokenCookie();
        response.addHeader("set-cookie", deletedRefreshTokenCookie.toString());

        customAuthenticationEntryPoint.commence(request, response, failed);
    }
}
