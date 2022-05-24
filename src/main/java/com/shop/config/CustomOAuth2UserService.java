package com.shop.config;

import com.shop.dto.OAuthAttributes;
import com.shop.dto.SessionMemberDto;
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

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String userRegistrationId = userRequest.getClientRegistration().getRegistrationId(); // 현제 로그인 진행중인 서비스를 구분할때 사용, ex 구글인지 네이버인지
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // 로그인 진행시 키가 되는 필드를 불러온다. 구글은 sub, 네이버 카카오는 지원하지 않는다.

        OAuthAttributes attributes = OAuthAttributes.of(userRegistrationId, userNameAttributeName, oAuth2User.getAttributes());// OAuthAttribute를 통해 OAuth2User의 attribute를 담은 클래스

        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("member", new SessionMemberDto(member));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // Update가 되었을떄 사용자의 이름이 프로필 사진이 변경되면 Member의 내용도 변경하도록 설계
    private Member saveOrUpdate(OAuthAttributes attributes){
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return  memberRepository.save(member);
    }
}
