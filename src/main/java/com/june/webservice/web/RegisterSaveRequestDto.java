package com.june.webservice.web;

import com.june.webservice.domain.register.Register;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterSaveRequestDto {

    private String key;
    private String description;
    private String generator;
    private int    minLength;

    @Builder
    public RegisterSaveRequestDto(String key, String description, String generator, int minLength) {
        this.key = key;
        this.description = description;
        this.generator = generator;
        this.minLength = minLength;
    }
    
    public Register toEntity(){
        return Register.builder()
                .key(key)
                .description(description)
                .generator(generator)
                .minLength(minLength)
                .build();
    }
}