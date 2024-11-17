package com.ssafy.living_spot.auth.service;

import static com.ssafy.living_spot.common.exception.ErrorMessage.NOT_EXIST_EMAIL;

import com.ssafy.living_spot.member.domain.PrincipalDetail;
import com.ssafy.living_spot.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PrincipalDetail principalDetail = new PrincipalDetail(
                memberService.findMemberByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(NOT_EXIST_EMAIL.getMessage()))
        );
        return principalDetail;
    }
}