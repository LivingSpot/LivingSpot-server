package com.ssafy.living_spot.house.controller;

import com.ssafy.living_spot.house.dto.HouseDealDto;
import com.ssafy.living_spot.house.service.HouseService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
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
//@MapperScan("com.ssafy.HappyHome.house.mapper") // Mapper 패키지 지정
public class HouseController {

    private final HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService){
        this.houseService = houseService;
    }

    @GetMapping("/searchAll")
    public ResponseEntity<HouseDealDto> getAll(){
        List<HouseDealDto> list = houseService.searchAll();

        return ResponseEntity.status(HttpStatus.OK).body((HouseDealDto) list);
    }

    @GetMapping("/searchByAptName")
    public ResponseEntity<List<HouseDealDto>> searchByAptName(@RequestParam(name = "aptName") String aptName) {
        List<HouseDealDto> list = houseService.getListByAptName(aptName);
        System.out.println(list);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/searchByDongName")
    public ResponseEntity<List<HouseDealDto>> searchByDongName(@RequestParam String dongName) {
        List<HouseDealDto> list = houseService.getListByDongName(dongName);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/searchByDealDate")
    public ResponseEntity<List<HouseDealDto>> searchByDealDate(@RequestParam Integer dealYear, @RequestParam Integer dealMonth) {
        List<HouseDealDto> list = houseService.getListByDealDate(dealYear, dealMonth);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
