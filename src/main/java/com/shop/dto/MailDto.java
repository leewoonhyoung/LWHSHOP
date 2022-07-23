package com.shop.dto;


import com.shop.entity.Member;
import lombok.*;

@Getter
@Setter // todo setter 제거후 builder 형성
@AllArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private String message;

    }

