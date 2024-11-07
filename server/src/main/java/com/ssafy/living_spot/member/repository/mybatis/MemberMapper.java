package com.ssafy.living_spot.member.repository.mybatis;

import com.ssafy.living_spot.member.domain.Member;
import com.ssafy.living_spot.member.dto.MemberIdParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    Member selectMemberById(MemberIdParam memberIdParam);
}
