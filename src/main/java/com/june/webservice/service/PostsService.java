package com.june.webservice.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.june.webservice.domain.posts.PostsRepository;
import com.june.webservice.web.PostsSaveRequestDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PostsService {
    private PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto dto){
        return postsRepository.save(dto.toEntity()).getId();
    }
}