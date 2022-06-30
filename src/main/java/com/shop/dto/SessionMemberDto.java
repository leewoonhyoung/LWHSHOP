package com.shop.dto;

import com.shop.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

//OAuthAttributes는 CustomOAuth2UserService의 공용 dto 클래스다.
//SessionMember 에서는 인증된 사용자만을 담은 dto 입니다.
@Getter
public class SessionMemberDto implements Serializable {

    private String name;
    private String email;
    private String picture;


    @Builder
    public SessionMemberDto(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
