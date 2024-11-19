package com.ssafy.living_spot.external.api;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naverApiClient", url = "https://openapi.naver.com")
public interface NaverApiClient {
    @GetMapping("/v1/nid/me")
    Map<String, Object> getUserInformation(@RequestHeader("Authorization") String token);
}
