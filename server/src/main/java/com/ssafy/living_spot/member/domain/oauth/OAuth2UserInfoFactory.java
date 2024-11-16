package com.ssafy.living_spot.member.domain.oauth;

import static com.ssafy.living_spot.common.exception.ErrorMessage.*;

import com.ssafy.living_spot.common.exception.BadRequestException;
import com.ssafy.living_spot.member.domain.oauth.impl.GoogleUserInfo;
import com.ssafy.living_spot.member.domain.oauth.impl.KakaoUserInfo;
import com.ssafy.living_spot.member.domain.oauth.impl.NaverUserInfo;
import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        switch (registrationId) {
            case "google":
                return new GoogleUserInfo(attributes);
            case "naver":
                return new NaverUserInfo(attributes);
            case "kakao":
                return new KakaoUserInfo(attributes);
            default:
                throw new BadRequestException(UNSUPPORTED_PROVIDER);
        }
    }
}
