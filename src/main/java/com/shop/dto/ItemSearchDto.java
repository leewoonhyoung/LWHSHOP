package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    //현제 시간과 상품 등록일을 비교하는 필드
    private String searchDateType;

    //상품 판매상태를 기준으로 상품 데이터를 조회
    private ItemSellStatus searchSellStatus;

    //상품을 조회할 때 어떤 상품 유형으로 조회
    private String searchBy;

    //조회할 검색어를 저장 변수.
    private String searchQuery = "";

}
