package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String picture;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());

        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;

    }

    @Builder
    public Member(){
    }

    @Builder
    public Member(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public Member update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
