package com.ssafy.living_spot.house.controller;

import com.ssafy.living_spot.house.dto.HouseDealDto;
import com.ssafy.living_spot.house.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/house")
@Slf4j
//@MapperScan("com.ssafy.HappyHome.house.mapper") // Mapper 패키지 지정
public class HouseController {

    private final HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService){
        this.houseService = houseService;
    }

    @GetMapping("/searchAll")
    public ResponseEntity<List<HouseDealDto>> searchAll(){
        List<HouseDealDto> searchedAllList = houseService.searchAll();

        return ResponseEntity.status(HttpStatus.OK).body(searchedAllList);
    }

    @GetMapping("/searchDetail")
    public ResponseEntity<List<HouseDealDto>> searchDetail(@RequestParam(name="dongName") String dongName,
                                                           @RequestParam(name="aptName") String aptName,
                                                           @RequestParam(name="dealYear") Integer dealYear,
                                                           @RequestParam(name="dealMonth") Integer dealMonth
                                                           ){
        log.info("상세검색 백엔드 API 호출 중!!!!");
        List<HouseDealDto> searchedDetailList = houseService.searchDetail(dongName,aptName,dealYear,dealMonth);

        return  ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, "application/json") .body(searchedDetailList);
    }

    @GetMapping("/searchByAptName")
    public ResponseEntity<List<HouseDealDto>> searchByAptName(@RequestParam(name = "aptName") String aptName) {
        List<HouseDealDto> searchedByAptNameList = houseService.getListByAptName(aptName);

        return ResponseEntity.status(HttpStatus.OK).body(searchedByAptNameList);
    }

    @GetMapping("/searchByDongName")
    public ResponseEntity<List<HouseDealDto>> searchByDongName(@RequestParam String dongName) {
        List<HouseDealDto> searchedByDongNameList = houseService.getListByDongName(dongName);
        return ResponseEntity.status(HttpStatus.OK).body(searchedByDongNameList);
    }

    @GetMapping("/searchByDealDate")
    public ResponseEntity<List<HouseDealDto>> searchByDealDate(@RequestParam Integer dealYear, @RequestParam Integer dealMonth) {
        List<HouseDealDto> searchedByDealDateList = houseService.getListByDealDate(dealYear, dealMonth);
        return ResponseEntity.status(HttpStatus.OK).body(searchedByDealDateList);
    }
}
