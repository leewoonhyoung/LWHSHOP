package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LogoutRequest {
    @NotBlank
    private String refreshToken;


    @Builder
    public LogoutRequest(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
