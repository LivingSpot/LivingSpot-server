package com.ssafy.living_spot.external.api;

import com.ssafy.living_spot.member.domain.oauth.impl.KakaoUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoApiClient", url = "https://kapi.kakao.com")
public interface KakaoApiClient {
    @GetMapping(value = "/v2/user/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    KakaoUserInfo getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
