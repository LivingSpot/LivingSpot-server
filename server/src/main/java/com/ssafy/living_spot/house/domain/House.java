package com.ssafy.living_spot.house.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "houseinfos", schema = "ssafyhome")
public class House {
    @Id
    @Column(name = "apt_seq", nullable = false, length = 20)
    private String aptSeq;

    @Column(name = "sgg_cd", length = 5)
    private String sggCd;

    @Column(name = "umd_cd", length = 5)
    private String umdCd;

    @Column(name = "umd_nm", length = 20)
    private String umdNm;

    @Column(name = "jibun", length = 10)
    private String jibun;

    @Column(name = "road_nm_sgg_cd", length = 5)
    private String roadNmSggCd;

    @Column(name = "road_nm", length = 20)
    private String roadNm;

    @Column(name = "road_nm_bonbun", length = 10)
    private String roadNmBonbun;

    @Column(name = "road_nm_bubun", length = 10)
    private String roadNmBubun;

    @Column(name = "apt_nm", length = 40)
    private String aptNm;

    @Column(name = "build_year")
    private Integer buildYear;

    @Column(name = "latitude", length = 45)
    private String latitude;

    @Column(name = "longitude", length = 45)
    private String longitude;
}