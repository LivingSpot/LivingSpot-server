package com.ssafy.living_spot.house.repository.mybatis;

import com.ssafy.living_spot.house.dto.HouseDealDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseMapper {

    List<HouseDealDto> searchAll();

    List<HouseDealDto> findByAptName(@Param("aptName") String aptName);

    List<HouseDealDto> findByDongName(@Param("dongName") String dongName);

    List<HouseDealDto> findByDealDate(@Param("dealYear") Integer dealYear, @Param("dealMonth") Integer dealMonth);

    List<HouseDealDto> searchDetail(@Param("dongName") String dongName,@Param("aptName") String aptNAme,@Param("dealYear") Integer dealYear, @Param("dealMonth") Integer dealMonth);
}
