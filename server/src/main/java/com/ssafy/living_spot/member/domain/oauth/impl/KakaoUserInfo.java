package com.ssafy.living_spot.member.domain.oauth.impl;

import com.ssafy.living_spot.member.domain.oauth.OAuth2UserInfo;
import java.util.Map;
import java.util.Optional;

public class KakaoUserInfo extends OAuth2UserInfo {
    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("account_email");
    }

    @Override
    public String getName() {
        return Optional.ofNullable(getResponse())
                .map(response -> (String) response.get("profile_nickname"))
                .orElse(null);
    }

    @Override
    public String getProfileImageUrl() {
        return Optional.ofNullable(getResponse())
                .map(response -> (String) response.get("profile_image"))
                .orElse(null);
    }
}
