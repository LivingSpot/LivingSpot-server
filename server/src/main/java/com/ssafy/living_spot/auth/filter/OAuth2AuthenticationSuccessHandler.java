package com.ssafy.living_spot.auth.filter;

import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.AUTHORIZATION_HEADER;
import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.BEARER_PREFIX;

import com.ssafy.living_spot.auth.dto.MemberTokenInfo;
import com.ssafy.living_spot.auth.dto.response.JwtToken;
import com.ssafy.living_spot.auth.jwt.component.JwtUtil;
import com.ssafy.living_spot.member.domain.Member;
import com.ssafy.living_spot.member.domain.PrincipalDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        log.info("OAuth2 로그인 성공");
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        Member member = principal.getMember();

        MemberTokenInfo memberTokenInfo = new MemberTokenInfo(member.getId(), member.getRole().toString());
        JwtToken jwtToken = jwtUtil.generateTokens(memberTokenInfo);

        ResponseCookie refreshTokenCookie = jwtUtil.createRefreshTokenCookie(jwtToken.refreshToken());
        response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + jwtToken.accessToken());
        response.addHeader("set-cookie", refreshTokenCookie.toString());

        log.info("OAuth2 로그인 성공: {}", member.getEmail());
    }
}
