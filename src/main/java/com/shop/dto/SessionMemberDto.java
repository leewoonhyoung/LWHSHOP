package com.shop.dto;

import com.shop.entity.Member;
import lombok.Getter;

import java.io.Serializable;


//SessionMember 에서는 인증된 사용자만을 담은 dto 입니다.
@Getter
public class SessionMemberDto implements Serializable {

    private String name;
    private String email;
    private String picture;


    public SessionMemberDto(Member user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
