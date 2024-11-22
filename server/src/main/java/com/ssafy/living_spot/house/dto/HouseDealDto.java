package com.ssafy.living_spot.house.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class HouseDealDto {
    private Integer no;
    private String aptSeq;
    private String aptDong;
    private String floor;
    private Integer dealYear;
    private Integer dealMonth;
    private Integer dealDay;
    private BigDecimal excluUseAr;
    private String dealAmount;
    private HouseInfoDto houseInfo;

    @Override
    public String toString() {
        return "아파트 정보: ["+getHouseInfo().toString()+"] 거래 일자: " + getDealYear()+ ". " + getDealMonth() + ". " + getDealDay() + " 거래 액: " + getDealAmount() +" 전용 면적: " + getExcluUseAr();
    }


}
