package com.june.webservice.domain.key;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.june.webservice.domain.BaseTimeEntity;
import com.june.webservice.domain.key.Key;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Key extends BaseTimeEntity  {

    @Id
    @Column(length = 100, nullable = false)
    private String key;
    
    @Builder
    public Key(String key) {
        this.key = key;
    }
}