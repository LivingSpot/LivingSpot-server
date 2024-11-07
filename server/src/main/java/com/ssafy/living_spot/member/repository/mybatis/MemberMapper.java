package com.ssafy.living_spot.member.repository.mybatis;

import com.ssafy.living_spot.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    Member selectMemberById(@Param("memberId") Long memberId);
}
