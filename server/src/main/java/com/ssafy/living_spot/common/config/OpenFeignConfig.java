package com.ssafy.living_spot.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableFeignClients(basePackages = {"com.ssafy.living_spot.external"})
public class OpenFeignConfig {

}
