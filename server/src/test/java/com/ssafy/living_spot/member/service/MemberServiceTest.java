package com.ssafy.living_spot.member.service;

import com.ssafy.living_spot.member.domain.Member;
import com.ssafy.living_spot.member.dto.request.MemberIdParam;
import com.ssafy.living_spot.member.dto.request.MemberSignUpRequest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    private Long memberId;

    @BeforeAll
    public void setUp() {
        MemberSignUpRequest memberSignUpRequest = new MemberSignUpRequest("이석환", "닉네임", "ghks@gmail.com", "1234");
        memberId = memberService.signUp(memberSignUpRequest);
    }

    @Order(1)
    @Test
    @DisplayName("JPA를 이용한 멤버 조회")
    public void JPA_멤버_조회() throws Exception {
        // when
        Member findMemberById = memberService.getMemberByJpa(new MemberIdParam(memberId));

        // then
        Assertions.assertThat(findMemberById.getId()).isEqualTo(memberId);
    }

    @Order(2)
    @Test
    @DisplayName("MyBatis를 이용한 멤버 조회")
    public void MyBatis_멤버_조회() throws Exception {
        // when
        Member findMemberById = memberService.getMemberByMybatis(new MemberIdParam(memberId));

        // then
        Assertions.assertThat(findMemberById.getId()).isEqualTo(memberId);
    }
}
