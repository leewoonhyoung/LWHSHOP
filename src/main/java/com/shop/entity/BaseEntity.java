package com.shop.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class BaseEntity extends BaseTimeEntity{

    //데이터 생성자 자동 저장 어노테이션
    @CreatedBy
    @Column(updatable = false)
    private String createBy;


    //데이터 수정자 자동 저장 어노테이션
    @LastModifiedBy
    private String modifiedBy;
}
