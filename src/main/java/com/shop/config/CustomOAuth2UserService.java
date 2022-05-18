package com.shop.config;

import com.shop.config.auth.OAuthAttributes;
import com.shop.config.auth.SessionUser;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String userRegistrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(userRegistrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("member", new SessionUser(member));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    //변경사항 Member 를 받어야 하나? 아니면 새로운 user entity를 만들까?

    private Member saveOrUpdate(OAuthAttributes attributes){
        Member member = memberRepository.findByEmail2(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return  memberRepository.save(member);
    }
}
