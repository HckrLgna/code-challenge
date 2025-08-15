package com.lgnasolutions.backend_challenge.application.services;

import com.lgnasolutions.backend_challenge.domain.dto.TagDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Tag;
import com.lgnasolutions.backend_challenge.domain.exceptions.CustomException;
import com.lgnasolutions.backend_challenge.domain.ports.TagRepository;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    public TagDTO create(UUID userId, TagDTO tagDTO) {
        if ( tagRepository.findByUserIdAndName( userId, tagDTO.getName()).isPresent()){
            throw new CustomException("Tag already exists for user: " + userId + " with name: " + tagDTO.getName());
        }
        Tag tag = Tag.builder()
                .userId(userId)
                .name(tagDTO.getName())
                .color(tagDTO.getColor())
                .build();
        Tag created = tagRepository.create(tag);
        return TagMapper.toDTO(created);
    }
    public List<TagDTO> getTags(UUID userId){
        return tagRepository.findByUserId(userId)
                .stream()
                .map(TagMapper::toDTO)
                .collect(Collectors.toList());
    }
    public void delete(UUID userId, UUID tagId){

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()-> new CustomException("Tag does not exist : " + tagId));
        tagRepository.delete(tag.getId());
    }
}
