package com.june.webservice.web;

import com.june.webservice.domain.key.Key;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KeySaveRequestDto {

    private String key;
  
    @Builder
    public KeySaveRequestDto(String key, String keyId) {
        this.key = key;
    }
    
    public Key toEntity(){
        return Key.builder()
                .key(key)
                .build();
    }
}