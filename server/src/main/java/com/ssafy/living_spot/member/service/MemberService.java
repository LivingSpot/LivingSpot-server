package com.ssafy.living_spot.member.service;

import com.ssafy.living_spot.member.domain.Member;
import com.ssafy.living_spot.member.dto.MemberIdParam;
import com.ssafy.living_spot.member.repository.mybatis.MemberMapper;
import com.ssafy.living_spot.member.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public Long saveMember(Member member) {
        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public Member getMemberByJpa(MemberIdParam memberIdParam) {
       return memberRepository.findById(memberIdParam.id()).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Member getMemberByMybatis(MemberIdParam memberIdParam) {
        return memberMapper.selectMemberById(memberIdParam.id());
    }
}