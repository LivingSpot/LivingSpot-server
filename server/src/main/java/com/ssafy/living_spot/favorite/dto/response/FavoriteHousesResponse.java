package com.ssafy.living_spot.favorite.dto.response;

import java.util.List;

public record FavoriteHousesResponse(
        List<HouseInfoResponse> favoriteHouses
) {
}
