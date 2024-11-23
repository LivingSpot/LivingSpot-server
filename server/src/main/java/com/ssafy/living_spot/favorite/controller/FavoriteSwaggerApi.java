package com.ssafy.living_spot.favorite.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface FavoriteSwaggerApi {

    @ApiResponse(responseCode = "200", description = "즐겨찾기 추가/삭제")
    ResponseEntity<?> toggleFavorite(@RequestParam String aptSeq);
}
