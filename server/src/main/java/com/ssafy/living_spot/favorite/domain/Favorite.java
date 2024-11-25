package com.ssafy.living_spot.favorite.domain;

import com.ssafy.living_spot.common.auditing.BaseTimeEntity;
import com.ssafy.living_spot.house.domain.HouseDeal;
import com.ssafy.living_spot.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "favorite")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Favorite extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "housedeal_no", nullable = false)
    private HouseDeal houseDeal;

    @Builder
    public Favorite(Member member, HouseDeal houseDeal) {
        this.member = member;
        this.houseDeal = houseDeal;
    }
}
