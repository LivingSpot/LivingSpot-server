package com.ssafy.living_spot.member.service;

import static com.ssafy.living_spot.common.Exception.ErrorMessage.ALREADY_EXIST_EMAIL;

import com.ssafy.living_spot.common.Exception.BadRequestException;
import com.ssafy.living_spot.member.domain.Member;
import com.ssafy.living_spot.member.dto.request.MemberIdParam;
import com.ssafy.living_spot.member.dto.request.MemberSignUpRequest;
import com.ssafy.living_spot.member.repository.jpa.MemberRepository;
import com.ssafy.living_spot.member.repository.mybatis.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long signUp(MemberSignUpRequest memberSignUpRequest) {
        if (memberRepository.existsByEmail(memberSignUpRequest.email())) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL);
        }

        Member member = Member.builder()
                .name(memberSignUpRequest.name())
                .nickname(memberSignUpRequest.nickname())
                .email(memberSignUpRequest.email())
                .password(bCryptPasswordEncoder.encode(memberSignUpRequest.password()))
                .build();

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