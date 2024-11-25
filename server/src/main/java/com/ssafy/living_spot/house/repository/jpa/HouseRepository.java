package com.ssafy.living_spot.house.repository.jpa;

import com.ssafy.living_spot.house.domain.House;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    Optional<House> findByAptSeq(String aptSeq);

    @Query(value = "SELECT h.apt_seq AS aptSeq, h.sgg_cd AS sggCd, h.umd_cd AS umdCd, h.umd_nm AS umdNm, " +
            "h.jibun AS jibun, h.road_nm_sgg_cd AS roadNmSggCd, h.road_nm AS roadNm, " +
            "h.road_nm_bonbun AS roadNmBonbun, h.road_nm_bubun AS roadNmBubun, " +
            "h.apt_nm AS aptNm, h.build_year AS buildYear, h.latitude AS latitude, h.longitude AS longitude, " +
            "hd.floor AS floor, hd.exclu_use_ar AS excluUseAr, hd.deal_amount AS dealAmount, hd.no " +
            "FROM houseinfos h " +
            "JOIN housedeals hd ON h.apt_seq = hd.apt_seq " +
            "WHERE hd.no IN (:favoriteHouseDealId)", nativeQuery = true)
    List<Object[]> findAllHouseByHouseDeal(@Param("favoriteHouseDealId") List<String> favoriteHouseDealId);
}
