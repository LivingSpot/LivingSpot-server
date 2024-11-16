package com.ssafy.living_spot.member.domain.oauth.impl;

import com.ssafy.living_spot.member.domain.oauth.OAuth2UserInfo;
import java.util.Map;
import java.util.Optional;

public class NaverUserInfo extends OAuth2UserInfo {
    public NaverUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getProviderId() {
        return Optional.ofNullable(getResponse())
                .map(response -> (String) response.get("id"))
                .orElse(null);
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return Optional.ofNullable(getResponse())
                .map(response -> (String) response.get("email"))
                .orElse(null);
    }

    @Override
    public String getName() {
        return Optional.ofNullable(getResponse())
                .map(response -> (String) response.get("nickname"))
                .orElse(null);
    }

    @Override
    public String getProfileImageUrl() {
        return Optional.ofNullable(getResponse())
                .map(response -> (String) response.get("profile_image"))
                .orElse(null);
    }
}
