package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item {

    @Id@GeneratedValue
    @Column(name="item_id")
    private Long id; // 상품 코드

    @Column(nullable = false,length = 50)
    private String itemNm; // 상품명

    @Column(name = "price", nullable = false)
    private int price;// 상품가격

    @Column(nullable = false)
    private int stockNumber; // 재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail;//아이템 상품 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    private LocalDateTime regTime; //등록시간
    private LocalDateTime updateTime; // 수정 시간
}
