package com.ssafy.living_spot.auth.service;

import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.AUTHORIZATION_HEADER;
import static com.ssafy.living_spot.auth.jwt.component.JwtConstants.BEARER_PREFIX;

import com.ssafy.living_spot.auth.dto.request.GeneralLoginRequest;
import com.ssafy.living_spot.auth.exception.AuthExceptionType;
import com.ssafy.living_spot.auth.jwt.component.JwtUtil;
import com.ssafy.living_spot.common.exception.BadRequestException;
import com.ssafy.living_spot.common.exception.ErrorMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public void loginWithCredentials(
            GeneralLoginRequest generalLoginRequest,
            HttpServletResponse response
    ) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        generalLoginRequest.email(),
                        generalLoginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void extractOAuth2AccessTokenFromCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String accessToken = null;

        if(cookies == null){
            throw new BadRequestException(ErrorMessage.EMPTY_COOKIE);
        }

        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("access_token")){
                accessToken = cookie.getValue();
                break;
            }
        }

        if(accessToken == null){
            throw new BadRequestException(ErrorMessage.INVALID_ACCESS_TOKEN);
        }

        ResponseCookie deletedAccessTokenCookie = jwtUtil.deleteAccessTokenCookie();
        response.addHeader("set-cookie", deletedAccessTokenCookie.toString());
        System.out.println(accessToken);
        response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + accessToken);
    }
}
