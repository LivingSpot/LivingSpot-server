package com.ssafy.living_spot.auth.jwt.service;

import static com.ssafy.living_spot.common.exception.ErrorMessage.NOT_EXIST_EMAIL;

import com.ssafy.living_spot.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberService.findMemberByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(NOT_EXIST_EMAIL.getMessage()));
    }
}