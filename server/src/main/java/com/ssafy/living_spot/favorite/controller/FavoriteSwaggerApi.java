package com.ssafy.living_spot.favorite.controller;

import com.ssafy.living_spot.favorite.dto.response.FavoriteHousesResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface FavoriteSwaggerApi {

    @ApiResponse(responseCode = "200", description = "찜 목록 추가/삭제")
    ResponseEntity<?> toggleFavorite(@RequestParam String aptSeq);

    @ApiResponse(responseCode = "200", description = "찜 목록 목록 조회")
    ResponseEntity<FavoriteHousesResponse> getFavoriteList();
}
