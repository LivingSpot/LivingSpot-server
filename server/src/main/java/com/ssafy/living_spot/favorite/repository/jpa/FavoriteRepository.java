package com.ssafy.living_spot.favorite.repository.jpa;

import com.ssafy.living_spot.favorite.domain.Favorite;
import com.ssafy.living_spot.house.domain.HouseDeal;
import com.ssafy.living_spot.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByMemberAndHouseDeal(Member member, HouseDeal houseDeal);

    @Query("select f.houseDeal.no from Favorite f where f.member.id = :memberId")
    List<String> findAllHouseDealByMemberId(@Param("memberId") Long memberId);
}
