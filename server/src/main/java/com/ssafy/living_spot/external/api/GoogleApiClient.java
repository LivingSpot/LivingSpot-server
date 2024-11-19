package com.ssafy.living_spot.external.api;

import com.ssafy.living_spot.member.domain.oauth.impl.GoogleUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleApiClient", url = "https://oauth2.googleapis.com")
public interface GoogleApiClient {

    @PostMapping(value = "/tokeninfo, consumes = MediaType.APPLICATION_JSON_VALUE")
    GoogleUserInfo getUserInfo(
            @RequestParam("id_token") String idToken
    );
}
