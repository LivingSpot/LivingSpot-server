package com.ssafy.living_spot.house.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HouseInfoDto {
    private String aptSeq;
    private String sggCd;
    private String umdCd;
    private String umdNm;
    private String jibun;
    private String roadNmSggCd;
    private String roadNm;
    private String roadNmBonbun;
    private String roadNmBubun;
    private String aptNm;
    private Integer buildYear;
    private String latitude;
    private String longitude;



    @Override
    public String toString() {
        return "아파트 이름: " + getAptNm() +" 도로명 주소: " + getRoadNm() + " 건축 연도: " + getBuildYear();

//		return "HouseInfoDto [aptSeq=" + aptSeq + ", sggCd=" + sggCd + ", umdCd=" + umdCd + ", umdNm=" + umdNm
//				+ ", jibun=" + jibun + ", roadNmSggCd=" + roadNmSggCd + ", roadNm=" + roadNm + ", roadNmBonbun="
//				+ roadNmBonbun + ", roadNmBubun=" + roadNmBubun + ", aptNm=" + aptNm + ", buildYear=" + buildYear
//				+ ", latitude=" + latitude + ", longitude=" + longitude + ", getAptSeq()=" + getAptSeq()
//				+ ", getSggCd()=" + getSggCd() + ", getUmdCd()=" + getUmdCd() + ", getUmdNm()=" + getUmdNm()
//				+ ", getJibun()=" + getJibun() + ", getRoadNmSggCd()=" + getRoadNmSggCd() + ", getRoadNm()="
//				+ getRoadNm() + ", getRoadNmBonbun()=" + getRoadNmBonbun() + ", getRoadNmBubun()=" + getRoadNmBubun()
//				+ ", getAptNm()=" + getAptNm() + ", getBuildYear()=" + getBuildYear() + ", getLatitude()="
//				+ getLatitude() + ", getLongitude()=" + getLongitude() + ", getClass()=" + getClass() + ", hashCode()="
//				+ hashCode() + ", toString()=" + super.toString() + "]";
    }

}
