package com.june.webservice.domain.register;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.june.webservice.domain.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Register extends BaseTimeEntity {

    @Id
    @Column(length = 100, nullable = false)
    private String key;

    private String description;

    private String type;
    
    private String generator;
    
    private int    minLength;

    @Builder
    public Register(String key, String description, String type, String generator, int minLength) {
        this.key = key;
        this.description = description;
        this.type = type;
        this.generator = generator;
        this.minLength = minLength;
    }
}