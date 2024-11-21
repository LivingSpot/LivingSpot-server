package com.ssafy.living_spot.member.domain.oauth.impl;

import com.ssafy.living_spot.member.domain.oauth.AuthProvider;
import com.ssafy.living_spot.member.domain.oauth.OAuth2UserInfo;
import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo {
    private Map<String, Object> kakaoAccount;
    private Map<String, Object> profile;

    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
        this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.profile = (Map<String, Object>) kakaoAccount.get("profile");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.KAKAO;
    }

    @Override
    public String getName() {
        return profile.get("nickname").toString();
    }

    @Override
    public String getEmail() {
        return kakaoAccount.get("email").toString();
    }

    @Override
    public String getProfileImageUrl() {
        return profile.get("profile_image_url").toString();
    }


}
