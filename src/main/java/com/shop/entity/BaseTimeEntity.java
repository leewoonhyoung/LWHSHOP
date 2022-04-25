package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;


//SpringJPA 환경에서 Auditing을 적용하기 위해서는 @EntityListeners 어노테이션을 추가한다.
@EntityListeners(value={AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseTimeEntity {


    //엔티티가 저장될 때 시간을 자동으로 저장한다.
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    //엔티티값이 수정될때 시간을 자동으로 저장한다.
    @LastModifiedDate
    private LocalDateTime updateTime;
}
