package com.ssafy.living_spot.member.service;

import com.ssafy.living_spot.member.domain.Member;
import com.ssafy.living_spot.member.dto.MemberIdParam;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    private Member member;

    @BeforeEach
    public void setUp() {
        member = new Member(1L, "이석환", "ghks@gmail.com", "1234");
        memberService.saveMember(member);
    }

    @Test
    @DisplayName("JPA를 이용한 멤버 조회")
    public void JPA_멤버_조회() throws Exception {
        // when
        Member findMemberById = memberService.getMemberByJpa(new MemberIdParam(member.getId()));

        // then
        Assertions.assertThat(findMemberById.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("MyBatis를 이용한 멤버 조회")
    public void MyBatis_멤버_조회() throws Exception {
        // when
        Member findMemberById = memberService.getMemberByMybatis(new MemberIdParam(member.getId()));

        // then
        Assertions.assertThat(findMemberById.getId()).isEqualTo(member.getId());
    }


}
