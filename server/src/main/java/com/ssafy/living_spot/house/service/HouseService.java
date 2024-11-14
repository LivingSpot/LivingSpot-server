package com.ssafy.living_spot.house.service;

import com.ssafy.living_spot.house.dto.HouseDealDto;

import java.util.List;


public interface HouseService {
    public List<HouseDealDto> searchAll();
    List<HouseDealDto> getListByAptName(String aptName);
    List<HouseDealDto> getListByDongName(String dongName);
    List<HouseDealDto> getListByDealDate(Integer dealYear, Integer dealMonth);
}
