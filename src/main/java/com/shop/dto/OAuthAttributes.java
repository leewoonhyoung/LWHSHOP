package com.shop.dto;

import com.shop.constant.Role;
import com.shop.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;


//OAuthAttributes는 CustomOAuth2UserService의 공용 dto 클래스다.
//SessionMemberdto는  인증된 사용자만의 위한 dto이다! 해깔리지 말기
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;


    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }



        public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
            if("naver".equals(registrationId)){
                return ofNaver("id", attributes);
            }
    // oauth2User에서 반환하는 사용자 정보는 Map이다. 따라서 하나하나를 변환해서 반환해야 한다.
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        //naver 는 response 추가
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
