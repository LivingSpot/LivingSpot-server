package com.ssafy.living_spot.favorite.dto.response;

import java.math.BigDecimal;

public record HouseInfoResponse(
        String aptSeq,
        String sggCd,
        String umdCd,
        String umdNm,
        String jibun,
        String roadNmSggCd,
        String roadNm,
        String roadNmBonbun,
        String roadNmBubun,
        String aptNm,
        Integer buildYear,
        String latitude,
        String longitude,
        String floor,
        BigDecimal excluUseAr,
        String dealAmount,
        Integer no
) {
}
