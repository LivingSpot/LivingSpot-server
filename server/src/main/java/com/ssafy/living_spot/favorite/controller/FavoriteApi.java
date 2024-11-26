package com.ssafy.living_spot.favorite.controller;

import com.ssafy.living_spot.common.util.SecurityUtil;
import com.ssafy.living_spot.favorite.dto.response.FavoriteHousesResponse;
import com.ssafy.living_spot.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteApi implements FavoriteSwaggerApi {

    private final FavoriteService favoriteService;

    @Override
    @PostMapping("/toggle")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> toggleFavorite(@RequestParam("houseDealNo") Long houseDealNo) {
        Long memberId = SecurityUtil.getAuthenticatedMemberId();
        favoriteService.toggleFavorite(memberId, houseDealNo);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteFavorite(@RequestParam("houseDealNo") Long houseDealNo) {
        Long memberId = SecurityUtil.getAuthenticatedMemberId();
        favoriteService.deleteFavorite(memberId, houseDealNo);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FavoriteHousesResponse> getFavoriteList() {
        Long memberId = SecurityUtil.getAuthenticatedMemberId();
        return ResponseEntity.ok().body(favoriteService.getFavoriteHouses(memberId));
    }
}
