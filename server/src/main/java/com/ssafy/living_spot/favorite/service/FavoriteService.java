package com.ssafy.living_spot.favorite.service;

import com.ssafy.living_spot.common.exception.BadRequestException;
import com.ssafy.living_spot.common.exception.ErrorMessage;
import com.ssafy.living_spot.favorite.domain.Favorite;
import com.ssafy.living_spot.favorite.dto.response.FavoriteHousesResponse;
import com.ssafy.living_spot.favorite.repository.jpa.FavoriteRepository;
import com.ssafy.living_spot.house.domain.House;
import com.ssafy.living_spot.house.dto.HouseInfoDto;
import com.ssafy.living_spot.house.repository.jpa.HouseRepository;
import com.ssafy.living_spot.member.domain.Member;
import com.ssafy.living_spot.member.repository.jpa.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;

    @Transactional
    public void toggleFavorite(
            Long memberId,
            String aptSeq
    ) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ErrorMessage.NOT_EXIST_MEMBER));
        House house = houseRepository.findByAptSeq(aptSeq).orElseThrow(() -> new BadRequestException(ErrorMessage.NOT_EXIST_HOUSE));

        favoriteRepository.findByMemberAndHouse(member, house)
                .ifPresentOrElse(
                        favoriteRepository::delete,
                        () -> favoriteRepository.save(Favorite.builder()
                                .member(member)
                                .house(house)
                                .build())
                );
    }

    public FavoriteHousesResponse getFavoriteHouses(Long memberId) {
        List<HouseInfoDto> favoriteHouses = favoriteRepository.findAllByMemberId(memberId);
        return new FavoriteHousesResponse(favoriteHouses);
    }
}
