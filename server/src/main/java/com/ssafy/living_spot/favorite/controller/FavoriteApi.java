package com.ssafy.living_spot.favorite.controller;

import com.ssafy.living_spot.common.util.SecurityUtil;
import com.ssafy.living_spot.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> toggleFavorite(@RequestParam("aptSeq") String aptSeq) {
        Long memberId = SecurityUtil.getAuthenticatedMemberId();
        favoriteService.toggleFavorite(memberId, aptSeq);
        return ResponseEntity.ok().build();
    }
}
