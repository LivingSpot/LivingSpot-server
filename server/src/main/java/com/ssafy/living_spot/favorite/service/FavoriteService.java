package com.ssafy.living_spot.favorite.service;

import com.ssafy.living_spot.common.exception.BadRequestException;
import com.ssafy.living_spot.common.exception.ErrorMessage;
import com.ssafy.living_spot.favorite.domain.Favorite;
import com.ssafy.living_spot.favorite.dto.response.FavoriteHousesResponse;
import com.ssafy.living_spot.favorite.dto.response.HouseInfoResponse;
import com.ssafy.living_spot.favorite.repository.jpa.FavoriteRepository;
import com.ssafy.living_spot.house.domain.HouseDeal;
import com.ssafy.living_spot.house.repository.jpa.HouseDealRepository;
import com.ssafy.living_spot.house.repository.jpa.HouseRepository;
import com.ssafy.living_spot.member.domain.Member;
import com.ssafy.living_spot.member.repository.jpa.MemberRepository;
import java.math.BigDecimal;
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
    private final HouseDealRepository houseDealRepository;

    @Transactional
    public void toggleFavorite(
            Long memberId,
            Long dealId
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.NOT_EXIST_MEMBER));
        HouseDeal houseDeal = houseDealRepository.findById(dealId)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.NOT_EXIST_HOUSE_DEAL));

        favoriteRepository.findByMemberAndHouseDeal(member, houseDeal)
                .ifPresentOrElse(
                        favoriteRepository::delete,
                        () -> favoriteRepository.save(Favorite.builder()
                                .member(member)
                                .houseDeal(houseDeal)
                                .build())
                );
    }

    public FavoriteHousesResponse getFavoriteHouses(Long memberId) {
        List<String> favoriteHousesDealId = favoriteRepository.findAllHouseDealByMemberId(memberId);
        List<Object[]> favoriteHousesList = houseRepository.findAllHouseByHouseDeal(favoriteHousesDealId);
        System.out.println(favoriteHousesList.size());
        List<HouseInfoResponse> houseInfoResponses = favoriteHousesList.stream().map(house -> {
            BigDecimal excluUseAr = house[14] != null ? (BigDecimal) house[14] : BigDecimal.ZERO;
            return new HouseInfoResponse(
                    (String) house[0],
                    (String) house[1],
                    (String) house[2],
                    (String) house[3],
                    (String) house[4],
                    (String) house[5],
                    (String) house[6],
                    (String) house[7],
                    (String) house[8],
                    (String) house[9],
                    (Integer) house[10],
                    (String) house[11],
                    (String) house[12],
                    (String) house[13],
                    excluUseAr,
                    (String) house[15],
                    (Integer) house[16]
            );
        }).toList();

        return new FavoriteHousesResponse(houseInfoResponses);
    }

    public void deleteFavorite(
            Long memberId, Long houseDealNo
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.NOT_EXIST_MEMBER));
        HouseDeal houseDeal = houseDealRepository.findById(houseDealNo)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.NOT_EXIST_HOUSE_DEAL));
        favoriteRepository.findByMemberAndHouseDeal(member, houseDeal).ifPresent(favoriteRepository::delete);
    }
}
