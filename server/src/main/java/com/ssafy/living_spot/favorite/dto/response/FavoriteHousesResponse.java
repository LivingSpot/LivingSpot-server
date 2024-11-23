package com.ssafy.living_spot.favorite.dto.response;

import com.ssafy.living_spot.house.dto.HouseInfoDto;
import java.util.List;

public record FavoriteHousesResponse(
        List<HouseInfoDto> favoriteHouses
) {
}
