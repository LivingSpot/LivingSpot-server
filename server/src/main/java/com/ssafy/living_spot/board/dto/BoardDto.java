package com.ssafy.living_spot.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter @Setter
public class BoardDto {
    private int articleNo;
    private BigInteger id;
    private String title;
    private String content;
    private String type;
    private int hit;
    private String registerTime;
}

