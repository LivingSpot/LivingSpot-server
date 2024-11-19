package com.ssafy.living_spot.auth.service;

import com.ssafy.living_spot.auth.dto.request.AuthCodeRequest;
import com.ssafy.living_spot.auth.dto.request.GeneralLoginRequest;
import com.ssafy.living_spot.auth.jwt.component.JwtConstants;
import com.ssafy.living_spot.common.properties.OAuthProperties;
import com.ssafy.living_spot.common.properties.OAuthProperties.Provider;
import com.ssafy.living_spot.external.api.GoogleApiClient;
import com.ssafy.living_spot.external.api.GoogleAuthClient;
import com.ssafy.living_spot.external.api.KakaoApiClient;
import com.ssafy.living_spot.external.api.KakaoAuthClient;
import com.ssafy.living_spot.external.api.NaverAuthClient;
import com.ssafy.living_spot.external.response.GoogleTokenResponse;
import com.ssafy.living_spot.external.response.KakaoTokenResponse;
import com.ssafy.living_spot.external.response.NaverTokenResponse;
import com.ssafy.living_spot.member.domain.oauth.impl.GoogleUserInfo;
import com.ssafy.living_spot.member.domain.oauth.impl.KakaoUserInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.Registration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final GoogleAuthClient googleAuthClient;
    private final KakaoAuthClient kakaoAuthClient;
    private final NaverAuthClient naverAuthClient;

    private final GoogleApiClient googleApiClient;
    private final KakaoApiClient kakaoApiClient;

    private final OAuthProperties oAuthProperties;
    private static final String AUTHORIZATION_CODE = "authorization_code";


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

    public void loginWithOauth(String provider, AuthCodeRequest authCodeRequest) {
        switch (provider) {
            case "google":
                getGoogleToken(authCodeRequest);
                break;
            case "kakao":
                getKakaoToken(authCodeRequest);
                break;
            case "naver":
                getNaverToken(authCodeRequest);
                break;
            default:
                break;
        }
    }

    private void getGoogleToken(AuthCodeRequest code) {
        Provider google = getProvider("google");
        GoogleTokenResponse googleTokenResponse = googleAuthClient.getAccessToken(
                code.authorizationCode(),
                AUTHORIZATION_CODE,
                google.getClientId(),
                google.getClientSecret(),
                google.getRedirectUri());
        GoogleUserInfo userInfo = googleApiClient.getUserInfo(googleTokenResponse.idToken());
        System.out.println(userInfo.getEmail());
    }

    private void getKakaoToken(AuthCodeRequest code) {
        Provider kakao = getProvider("kakao");
        KakaoTokenResponse kakaoTokenResponse = kakaoAuthClient.getAccessToken(
                AUTHORIZATION_CODE,
                kakao.getClientId(),
                kakao.getRedirectUri(),
                code.authorizationCode(),
                kakao.getClientSecret());
        KakaoUserInfo user = kakaoApiClient.getUser(JwtConstants.BEARER_PREFIX + kakaoTokenResponse.accessToken());
        System.out.println(user.getEmail());
    }

    private void getNaverToken(AuthCodeRequest code) {
        Provider naver = getProvider("naver");
        NaverTokenResponse accessToken = naverAuthClient.getAccessToken(
                code.authorizationCode(),
                naver.getClientId(),
                naver.getClientSecret(),
                naver.getRedirectUri(),
                AUTHORIZATION_CODE);

    }

    private OAuthProperties.Provider getProvider(String providerName) {
        Registration registration = oAuthProperties.getRegistration().get(providerName);
        return new Provider(
                registration.getClientId(),
                registration.getClientSecret(),
                registration.getRedirectUri(),
                registration.getAuthorizationGrantType(),
                registration.getClientName(),
                registration.getScope()
        );
    }
}
