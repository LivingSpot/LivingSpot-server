package com.ssafy.living_spot.favorite.repository.jpa;

import com.ssafy.living_spot.favorite.domain.Favorite;
import com.ssafy.living_spot.house.domain.House;
import com.ssafy.living_spot.house.dto.HouseInfoDto;
import com.ssafy.living_spot.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByMemberAndHouse(Member member, House house);
    List<HouseInfoDto> findAllByMemberId(Long memberId);
}
