package com.ssafy.living_spot.auth.service;

import com.ssafy.living_spot.common.exception.BadRequestException;
import com.ssafy.living_spot.common.exception.ErrorMessage;
import com.ssafy.living_spot.member.domain.Member;
import com.ssafy.living_spot.member.domain.PrincipalDetail;
import com.ssafy.living_spot.member.domain.Role;
import com.ssafy.living_spot.member.domain.oauth.OAuth2UserInfo;
import com.ssafy.living_spot.member.domain.oauth.OAuth2UserInfoFactory;
import com.ssafy.living_spot.member.repository.jpa.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                userRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );
        String email = oAuth2UserInfo.getEmail();
        log.info("email: {}", email);
        log.info("provider: {}", oAuth2UserInfo.getProvider());
        log.info("providerId: {}", oAuth2UserInfo.getProviderId());
        log.info("name: {}", oAuth2UserInfo.getName());
        log.info("profileImageUrl: {}", oAuth2UserInfo.getProfileImageUrl());

        Optional<Member> optionalMember = memberRepository.findByEmail(oAuth2UserInfo.getEmail());
        Member member = optionalMember.orElse(null);
        if (member == null) {
            member = Member.builder()
                    .name(oAuth2UserInfo.getName())
                    .email(oAuth2UserInfo.getEmail())
                    .password("oauth2")
                    .role(Role.ROLE_USER)
                    .build();
            member.updateAuthProvider(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
            memberRepository.save(member);
        } else {
            throw new BadRequestException(ErrorMessage.ALREADY_EXIST_EMAIL);
        }

        return new PrincipalDetail(member, oAuth2User.getAttributes());
    }
}
