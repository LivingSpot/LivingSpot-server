package com.ssafy.living_spot.auth.filter;

import com.ssafy.living_spot.auth.dto.MemberTokenInfo;
import com.ssafy.living_spot.auth.dto.response.JwtToken;
import com.ssafy.living_spot.auth.jwt.component.JwtUtil;
import com.ssafy.living_spot.common.util.CookieUtil;
import com.ssafy.living_spot.member.domain.Member;
import com.ssafy.living_spot.member.domain.PrincipalDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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
    ) throws IOException, ServletException {
        log.info("OAuth2 로그인 성공");
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        Member member = principal.getMember();

        MemberTokenInfo memberTokenInfo = new MemberTokenInfo(member.getId(), member.getRole().toString());
        JwtToken jwtToken = jwtUtil.generateTokens(memberTokenInfo);
        log.info(jwtToken.accessToken());
        log.info(jwtToken.refreshToken());

        ResponseCookie accessTokenCookie = CookieUtil.createAccessTokenCookie(jwtToken.accessToken());
        ResponseCookie refreshTokenCookie = CookieUtil.createRefreshTokenCookie(jwtToken.refreshToken());
        response.addHeader("set-cookie", accessTokenCookie.toString());
        response.addHeader("set-cookie", refreshTokenCookie.toString());
        log.info("OAuth2 로그인 성공: {}", member.getEmail());

        String encodedName = URLEncoder.encode(member.getName(), "UTF-8");
        response.sendRedirect("http://localhost:5173/auth/oauth2-jwt-header?name=" + encodedName);
    }
}
