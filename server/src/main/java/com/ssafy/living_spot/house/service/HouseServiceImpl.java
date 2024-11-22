package com.ssafy.living_spot.house.service;


import com.ssafy.living_spot.house.dto.HouseDealDto;
import com.ssafy.living_spot.house.repository.mybatis.HouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {


    private final HouseMapper houseMapper;

    @Autowired
    public HouseServiceImpl(HouseMapper houseMapper){
        this.houseMapper = houseMapper;
    }

    @Override
    public List<HouseDealDto> searchAll() {
        return houseMapper.searchAll();
    }

    @Override
    public List<HouseDealDto> getListByAptName(String aptName) {
        return houseMapper.findByAptName(aptName);
    }

    @Override
    public List<HouseDealDto> getListByDongName(String dongName) {
        return houseMapper.findByDongName(dongName);
    }

    @Override
    public List<HouseDealDto> getListByDealDate(Integer dealYear, Integer dealMonth) {
        return houseMapper.findByDealDate(dealYear, dealMonth);
    }

    @Override
    public List<HouseDealDto> searchDetail(String dongName, String aptName, Integer dealYear, Integer dealMonth) {
        return houseMapper.searchDetail(dongName,aptName,dealYear,dealMonth);
    }
}
