package com.ssafy.living_spot.house.domain;

import com.ssafy.living_spot.favorite.domain.Favorite;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "housedeals", schema = "ssafyhome")
public class HouseDeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false)
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apt_seq", referencedColumnName = "apt_seq", foreignKey = @ForeignKey(name = "apt_seq_to_house_info"))
    private House house;

    @Column(name = "apt_dong", length = 40)
    private String aptDong;

    @Column(name = "floor", length = 3)
    private String floor;

    @Column(name = "deal_year")
    private Integer dealYear;

    @Column(name = "deal_month")
    private Integer dealMonth;

    @Column(name = "deal_day")
    private Integer dealDay;

    @Column(name = "exclu_use_ar", precision = 7, scale = 2)
    private BigDecimal excluUseAr;

    @Column(name = "deal_amount", length = 10)
    private String dealAmount;

    @OneToMany(mappedBy = "houseDeal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();
}
